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
import java.net.URL;
import java.io.InputStreamReader;
import java.io.InputStream;

import com.netscape.management.client.comm.CommClient;
import com.netscape.management.client.comm.CommRecord;
import com.netscape.management.client.comm.HttpManager;
import com.netscape.management.client.comm.HttpChannel;

public class Get implements CommClient, Runnable {
    public boolean finished = false;

    public static String user = "admin";
    public static String pw = "admin";
    public static String url = "http://skydome:30021/admin-serv/tasks/operation/authenticate";

    public static void main(String args[]) throws IOException {
        byte[] dt = { (byte) 224, (byte) 100, (byte) 109, (byte) 105,
        (byte) 110, (byte) 224 };

        String s = new String(dt);
        System.out.println(s);

        byte[] dt2 = s.getBytes("UTF8");
        for (int i = 0 ; i < dt2.length ; i++)
            System.out.println("byte[" + i + "] = " + dt2[i]);

        String s2 = new String(dt2, "UTF8");
        System.out.println(s2);

        user = pw = s2;

        byte [] dt3 = new byte[dt2.length + 1 + dt2.length];
        System.arraycopy(dt2, 0, dt3, 0, dt2.length);
        dt3[dt2.length] = ':';
        System.arraycopy(dt2, 0, dt3, dt2.length + 1, dt2.length);
        byte [] dt4 = HttpChannel.uuencode(dt3);
        System.out.println(new String(dt4));

        if (args.length > 0)
            user = args[0];

        if (args.length > 1)
            pw = args[1];

        if (args.length > 2)
            url = args[2];

        Thread t = new Thread(new Get());

        t.start();
    }

    public Get() { }

    public void run() {
        HttpManager h = new HttpManager();

        h.trace();
        h.setSendUTF8(true);

        try {
            long now1 = System.currentTimeMillis();
            h.get(new URL(url), this, null);
            await();
            long now2 = System.currentTimeMillis();
            System.out.println((now2 - now1));
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
        /*
         System.out.println("responseHandler:\n" + cr + '\n');

         int c;

         InputStreamReader isr = new InputStreamReader(response);

         try
         {
            while ((c = isr.read()) != -1)
         System.out.write((char)c);
         }
         catch (Exception e) { }
         */

        finish();
    }

    public void errorHandler(Exception exception, CommRecord cr) {
        System.err.println("errorHandler: " + exception);
        finish();
    }

    public String username(Object realm, CommRecord cr) {
        return user;
    }

    public String password(Object realm, CommRecord cr) {
        return pw;
    }
}
