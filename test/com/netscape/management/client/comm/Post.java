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
import java.util.Hashtable;

import com.netscape.management.client.comm.CommClient;
import com.netscape.management.client.comm.CommRecord;
import com.netscape.management.client.comm.HttpManager;
import com.netscape.management.client.comm.HttpChannel;

public class Post implements CommClient, Runnable {
    public boolean finished = false;

    public static void main(String args[]) throws IOException {
        Thread t = new Thread(new Post());

        t.start();
    }

    public Post() { }

    public void run() {
        HttpManager h = new HttpManager();

        h.trace();

        Hashtable ht = new Hashtable();
        ht.put("A", "1-1");
        ht.put("B", "2");
        ht.put("C", "3");

        try {
            ByteArrayInputStream data = HttpChannel.encode(ht);
            //h.post(new URL("http://awing:81/bogus"), this, null, data, data.available());
            h.post(new URL("http://skydome/write.cgi?file=tmp/dt"),
                    this, null, data, data.available());
            await();
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
        System.out.println("responseHandler:\n" + cr + '\n');

        int c;

        InputStreamReader isr = new InputStreamReader(response);

        try {
            while ((c = isr.read()) != -1)
                System.out.write((char) c);
        } catch (Exception e) { }

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
