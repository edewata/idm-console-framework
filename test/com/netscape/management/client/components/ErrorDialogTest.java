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
 * This Test is only used for testing the Error Dialog class
 * 
 * @author Thu Le
 */
class ErrorDialogTest extends JFrame
{   
    private JButton showButton;
    public ErrorDialogTest()
    {       
        Container contentPane = getContentPane();
        
        showButton = new JButton("Show Dialog");
        showButton.addActionListener(new ShowButtonListener());
        JPanel panel2 = new JPanel();
        panel2.setBorder(BorderFactory.createEtchedBorder());
        panel2.add(showButton);
        contentPane.add(panel2,BorderLayout.CENTER);
    }
    
    public class ShowButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String errLongText ="Can not log in because of an incorrect User ID, incorrect password, or Directory problem";
            String tipLongText ="User ID and password are case sensitive.\nMake sure that the CAP-LOCK key is not on";
            String errShortText = "Error";
            String tipShortText = "Tip";
            String detailShortText = "Details";
            String detailLongText = "HTTP Exception \nResponse HTTP/1.1 Unauthorized \nStatus 401 \nURL http://awing222:/admin-serv/authenticateSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";
            ErrorDialog errorDialog = new ErrorDialog(ErrorDialogTest.this, "Error", errLongText, tipLongText, detailLongText,ErrorDialog.YES_NO, ErrorDialog.YES);
            errorDialog.setIcon(ErrorDialog.WARNING_ICON);
            errorDialog.setLocation(100,100);
            errorDialog.setVisible(true);
        }
    }

    public static void main(String args[])
    {
        try {
            SuiLookAndFeel nmclf = new SuiLookAndFeel();
            UIManager.setLookAndFeel(nmclf);
        } catch (InternalError ie) {
            Debug.println("Console.common_init: " + ie.getMessage());
            System.exit(1);
        }
        catch (Exception e) {
            Debug.println("Console.common_init: Cannot init " + e);
        }
        ErrorDialogTest parent = new ErrorDialogTest();
        parent.setTitle("Error Dialog Test");       
        parent.setBounds(200,200,300,400);
   
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