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
package com.netscape.management.client.components.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import netscape.ldap.*;
import com.netscape.management.nmclf.*;
import com.netscape.management.client.components.*;
import com.netscape.management.client.util.*;

class DirBrowserTest
{
    public static void main(String []args)
    {
        try 
        {
            UIManager.setLookAndFeel(new SuiLookAndFeel());
            //UIManager.setLookAndFeel(new WindowsLookAndFeel());
            FontFactory.initializeLFFonts(); // load default customized fonts for login/splash
        }
        catch (Exception e) 
        {
            System.err.println("cannot load lf: " + e);
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

        LDAPConnection ldc; 
        
        try 
        {
            ldc = new LDAPConnection();
            ldc.connect("ntsbuild", 389);
            ldc.authenticate("cn=Directory Manager", "netscape");
            DirBrowserDialog esd = new DirBrowserDialog(frame, "Entry Selection Dialog", ldc);
			//esd.setBaseDN(baseDN);
            esd.setShowLeaf(true);				
			esd.initialize();
            esd.show();
            System.exit(0);
        } 
        catch (LDAPException e) 
        {
            System.err.println(e);
            System.exit(0);
        }
    }
}
