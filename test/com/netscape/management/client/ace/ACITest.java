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
package com.netscape.management.client.ace;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import netscape.ldap.*;
import com.netscape.management.client.components.*;
import com.netscape.management.nmclf.*;

/**
 * Invokes the ACIManager dialog.
 */
public class ACITest
{
	public static void main(String[] args)
	{
        //String aciDN = "ou=UserPreferences, ou=pacbell.net, o=NetscapeRoot";
        String aciDN = "o=NetscapeRoot";
        String userDN = "o=pacbell.net";
        LDAPConnection ldc = null;
        
        try 
        {
            UIManager.setLookAndFeel(new SuiLookAndFeel());
            FontFactory.initializeLFFonts(); // load default customized fonts for login/splash
        }
        catch (Exception e) 
        {
            System.err.println("cannot load lf: " + e);
        }

        try 
        {
            ldc = new LDAPConnection();
            //ldc.connect("crystite.pacbell.net", 389);
            ldc.connect("ntsbuild", 389);
            ldc.authenticate("cn=Directory Manager", "netscape");
        } 
        catch (LDAPException e)
        {
			int errorCode = e.getLDAPResultCode();
			System.err.println("LDAP Error " + errorCode + ": " + e.errorCodeToString(errorCode));
            System.exit(0);
        }
        
        JFrame frame = new JFrame();
		frame.addWindowListener
		(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e) 
				{
					System.exit(0);
				}
			}
		);

        JDialog d = new ACIManager(frame, "Messaging Server", ldc, aciDN, ldc, userDN);
		frame.pack();
		frame.show();
		frame.toFront();
        d.show();
        System.exit(0);
    }
}
