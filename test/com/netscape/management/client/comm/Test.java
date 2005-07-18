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
import java.io.ByteArrayInputStream;
import java.net.URL;

import com.netscape.management.client.comm.CommClient;
import com.netscape.management.client.comm.CommRecord;
import com.netscape.management.client.comm.HttpManager;

public class Test implements CommClient, Runnable {
    public boolean finished = false;
    public String value = null;

    public static void main(String args[]) throws IOException {
        Thread t = new Thread(new Test());

        t.start();
    }

    public Test() { }

    public void run() {
        HttpManager h = new HttpManager();

        h.trace();

        try {
            h.get(new URL("http://skydome/read.cgi?file=tmp/dt&expr=.*"),
                    this, null);
            awaitValue();

            if (value.charAt(0) == 'a')
                value = "beta=22";
            else
                value = "alpha=11";

            h.post(new URL("http://skydome/write.cgi?file=tmp/dt"),
                    this, null, new ByteArrayInputStream(value.getBytes()),
                    value.length());
            awaitSuccess();

            System.exit(0);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public synchronized void awaitValue() {
        while (value == null) {
            try {
                wait();
            } catch (Exception e) { }
        }
    }

    public synchronized void awaitSuccess() {
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

    public synchronized void setValue(String s) {
        value = s;
        System.out.println("value = " + value);
        notifyAll();
    }

    public void replyHandler(InputStream response, CommRecord cr) {
        try {

            InputStreamReader isr = new InputStreamReader(response);

            int c = isr.read();

            if (c == 'S') {
                finish();
                return;
            }

            String s = (char) c + "";

            while ((c = isr.read()) != -1)
                s += (char) c + "";
            setValue(s);

        } catch (Exception e) { }
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
