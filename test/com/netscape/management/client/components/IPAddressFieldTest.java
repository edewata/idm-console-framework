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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import com.netscape.management.nmclf.*;
import com.netscape.management.client.components.*;
import com.netscape.management.client.util.Debug;

/**
 * This IIPickerTest is only used for testing the IP Picker class
 * 
 * @author Thu Le
 */
class IPAddressFieldTest extends JFrame
{   
    private JButton showButton;
	
    public IPAddressFieldTest()
    {       
        Container contentPane = getContentPane();
        IPAddressField picker = new IPAddressField();
        picker.setIPAddress("209.76.109.91");
        contentPane.add(picker);
    }
   
    public static void main(String args[])
    {
		try 
		{
            SuiLookAndFeel nmclf = new SuiLookAndFeel();
            UIManager.setLookAndFeel(nmclf);
		}
		catch (InternalError ie) 
		{
            Debug.println("Console.common_init: " + ie.getMessage());
            System.exit(1);
        }
		catch (Exception e) 
		{
            Debug.println("Console.common_init: Cannot init " + e);
        }
        IPAddressFieldTest parent = new IPAddressFieldTest();
        parent.setTitle("IP Picker");       
        parent.setVisible(true);
        parent.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        parent.addWindowListener(new WindowAdapter(){
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
}