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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.netscape.management.client.comm.CommClient;
import com.netscape.management.client.comm.CommRecord;
import com.netscape.management.client.comm.HttpManager;

public class Test2 implements CommClient, Runnable {
    public boolean finished = false;
    public Thread thread = null;
    public int count = 0;
    public int numreqs = 7;

    public static void main(String args[]) throws IOException {
        Thread t = new Thread(new Test2());

        t.start();
    }

    public Test2() { }

    public void run() {
        HttpManager h = new HttpManager();

        //h.trace();
        h.setMaxChannels(4);
        h.setTimeout(10);

        thread = Thread.currentThread();

        try {
            for (int i = 0 ; i < numreqs ; i++) {
                h.get(new URL("http://skydome/read.cgi?file=tmp/dt" +
                        (i + 1) + "&expr=file"), this, null);
                thread.yield();
            }
            await();
            thread.sleep(1000);
            System.exit(0);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public synchronized void await() {
        while (!finished) {
            try {
                wait();
            } catch (Exception e) { }
        }
    }

    public synchronized void finish() {
        finished = true;
        notifyAll();
    }

    public void replyHandler(InputStream response, CommRecord cr) {
        System.out.println("responseHandler: " + cr.getTarget());

        InputStreamReader isr = new InputStreamReader(response);

        String s = "";

        try {
            int c;
            while ((c = isr.read()) != -1)
                s += (char) c;
        } catch (Exception e) { }

        System.out.println(s);
        if (s.substring(5, 7).equals("dt"))
            count++;

        if (count == numreqs)
            finish();
    }

    public void errorHandler(Exception exception, CommRecord cr) {
        System.err.println("errorHandler: " + exception);
        finish();
    }

    public String username(Object auth, CommRecord cr) {
        return "";
    }

    public String password(Object auth, CommRecord cr) {
        return "";
    }
}
