/** BEGIN COPYRIGHT BLOCK
 * Copyright (C) 2001 Sun Microsystems, Inc.  Used by permission.
 * Copyright (C) 2005 Red Hat, Inc.
 * All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version
 * 2.1 of the License.
 *                                                                                 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *                                                                                 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * END COPYRIGHT BLOCK **/
package com.netscape.management.client.comm.test;

import java.net.URL;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

import com.netscape.management.client.comm.CommClient;
import com.netscape.management.client.comm.CommRecord;
import com.netscape.management.client.comm.HttpManager;

public class AsyncTest implements CommClient, Runnable {
    static int count = 0;

    public HttpManager h;

    public static void main(String args[]) throws IOException {
        if (args.length > 0)
            count = Integer.parseInt(args[0]);

        Thread t = new Thread(new AsyncTest());

        t.start();
    }

    public AsyncTest() { }

    public synchronized void run() {
        h = new HttpManager();

        h.trace();
        h.setMaxChannels(2);
        h.setTimeout(1000);

        try {
            h.get(new URL("http://skydome/asynctest.cgi"), this, null,
                    h.ASYNC_RESPONSE);
            CommRecord cr = h.get(new URL("http://skydome/dummy.cgi"), this,
                    null, h.ASYNC_RESPONSE);
            h.terminate(cr);
            wait();
            h.get(new URL("http://skydome/asynctest.cgi"), this, null,
                    h.ASYNC_RESPONSE);
            wait();
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public synchronized void finish() {
        notifyAll();
    }

    public void replyHandler(InputStream response, CommRecord cr) {
        System.out.println("responseHandler:\n" + cr + '\n');

        int c;

        InputStreamReader isr = new InputStreamReader(response);

        try {
            if (count <= 0) {
                while ((c = isr.read()) != -1)
                    System.out.print((char) c);
            } else {
                while ((c = isr.read()) != -1) {
                    System.out.print((char) c);

                    if (c == '\n') {
                        if (--count == 0) {
                            h.terminate(cr);
                            break;
                        }
                    }
                }
            }
            finish();
        } catch (Exception e) { }
    }

    public void errorHandler(Exception exception, CommRecord cr) {
        System.err.println("errorHandler: " + exception);
        finish();
    }

    public String username(Object realm, CommRecord cr) {
        return "admin";
    }

    public String password(Object realm, CommRecord cr) {
        return "admin";
    }
}
