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
package com.netscape.management.client.acleditor;

import com.netscape.management.client.console.ConsoleInfo;
import com.netscape.management.client.util.Help;
import com.netscape.management.client.util.RemoteImage;
import com.netscape.management.client.util.ResourceSet;

import java.applet.Applet;

public class TestApplet extends Applet implements Runnable {
    // defaults
    String host = "skydome";
    String port = "10389";
    String userDN = "cn=admin, ou=People, o=Airius.com";
    String pw = "admin";
    String aclDN = "cn=tmorris, ou=people, o=Airius.com";
    String baseDN = "o=Airius.com";

    private Thread thread = null;
    private ACLEditor acled = null;

    public void init() { }

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

    public void run() {
        String param;

        if ((param = getParameter("host")) != null)
            host = param;
        if ((param = getParameter("port")) != null)
            port = param;
        if ((param = getParameter("userDN")) != null)
            userDN = param;
        if ((param = getParameter("pw")) != null)
            pw = param;
        if ((param = getParameter("aclDN")) != null)
            aclDN = param;
        if ((param = getParameter("baseDN")) != null)
            baseDN = param;

        RemoteImage.setApplet(this);
        ResourceSet.setApplet(this);
        Help.setApplet(this);

        ConsoleInfo ci = new ConsoleInfo();
        ci.setHost(host);
        ci.setPort(Integer.parseInt(port));
        ci.setAuthenticationDN(userDN);
        ci.setAuthenticationPassword(pw);
        ci.setAclDN(aclDN);
        ci.setBaseDN(baseDN);
        ci.setUserGroupDN(baseDN);
        ci.setApplet(this);
        ACLEditor ed = new ACLEditor(ci);
        ed.show();
    }

    public void showError(String s) {
        String errmsg = "TestApplet:error:" + s;
        showStatus(errmsg);
        System.err.println(errmsg);
    }
}


