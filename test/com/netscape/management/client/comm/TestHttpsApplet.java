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
import java.applet.Applet;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

import com.netscape.management.client.comm.CommClient;
import com.netscape.management.client.comm.CommRecord;
import com.netscape.management.client.comm.HttpManager;

public class TestHttpsApplet extends Applet implements Runnable,
CommClient {
    private Thread thread = null;

    public void init() {}

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null && thread.isAlive())
            thread.stop();
        thread = null;
    }

    public boolean finished = false;

    public void run() {
        HttpManager h = new HttpManager();

        h.trace();

        String[] optionalArgs = { "Header1: Bite Me", "Header2: Bite Me Big Time" };

        try {
            h.get(new URL("https://skydome:81/docs/abcd.html"), this, null);
            await();
            System.out.println("TestHttpsApplet finished.");
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

    public String username(Object realm, CommRecord cr) {
        return "admin";
    }

    public String password(Object realm, CommRecord cr) {
        return "admin";
    }
}
