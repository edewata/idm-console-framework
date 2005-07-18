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
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

import netscape.net.*;

public class TestHttpsApplet2 extends Applet implements Runnable {
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

    public void debug(String msg) {
        System.out.println(msg);
    }

    public void run() {
        try {

            SSLSocket socket = new SSLSocket("skydome", 81);

            debug("SSLSocket created");

            socket.forceHandshake();

            debug("handshake complete");

            BufferedOutputStream bos =
                    new BufferedOutputStream(socket.getOutputStream());

            String s;
            byte[] b;

            s = "GET /docs/abcd.html HTTP/1.1\r\n";
            b = s.getBytes();
            bos.write(b);
            bos.flush();
            debug(s);

            s = "Host: skydome:81\r\n";
            b = s.getBytes();
            bos.write(b);
            bos.flush();
            debug(s);

            s = "\r\n";
            b = s.getBytes();
            bos.write(b);
            bos.flush();
            debug(s);

            debug("request sent");

            BufferedInputStream bis =
                    new BufferedInputStream(socket.getInputStream());

            int c;

            while ((c = bis.read()) != -1)
                System.out.print((char) c);

            debug("EOF reached");

            socket.close();

            debug("socket closed");

        } catch (Exception e) {
            debug(e.toString());
        }
    }
}
