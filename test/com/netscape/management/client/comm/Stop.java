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
package netscape.management.client.comm;

import java.io.IOException;
import java.net.URL;

public class Stop implements CommClient, Runnable {
    public boolean finished = false;
    public String value = null;

    public static void main(String args[]) throws IOException {
        Thread t = new Thread(new Stop());

        t.start();
    }

    public Stop() { }

    public void run() {
        HttpManager h = new HttpManager();

        h.trace();

        try {
            h.send(new URL("http://bluemoon.mcom.com:23330/httpd-bluemoon/bin/shutdown?admin"),
                    null, this, null);
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

    public String username(Object authObject, CommRecord cr) {
        System.err.println("admin..............");
        return "admin";
    }

    public String password(Object authObject, CommRecord cr) {
        return "admin";
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

    public void replyHandler(Object response, CommRecord cr) {
        if (response != null) {
            byte[] b = (byte[]) response;

            String s = new String(b, 0);

            if (s.charAt(0) == 'S')
                finish();
            else
                setValue(s);
        }
    }

    public void errorHandler(Object error, CommRecord cr) {
        Exception e = (Exception) error;

        System.err.println("errorHandler: " + e);
        finish();
    }
}
