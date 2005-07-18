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
import com.netscape.management.client.util.Debug;

/**
 * This Test is only used for testing the Error Dialog class
 * 
 * @author Thu Le
 */
class StatusDialogTest extends JFrame
{   
    private JButton showButton;
    StatusDialog statusDialog;
    public MyThread thread;
    public StatusDialogTest()
    {       
        Container contentPane = getContentPane();
        
        showButton = new JButton("Show Status Dialog");
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
            String descriptionLine = "Production Directory Server";
            String progressLine = "Creating Configuration Files";
            String detailShortText = "Details";
            String detailLongText = "";
            statusDialog = new StatusDialog(StatusDialogTest.this,"Status Title", descriptionLine, StatusDialog.STATE_BUSY, StatusDialog.TASK_DETAIL);
            //statusDialog.addDetail("line1 \nline2 \nline3 \n");
            statusDialog.setLocation(100,100);
            statusDialog.setVisible(true);
            statusDialog.setConfirmationDialogEnabled(true);
            statusDialog.setProgressText("Task 1");
            //thread = new MyThread();
            //thread.start();
            for (int i = 1; i < 10; i++)
            {
                statusDialog.addDetail("Task " + i + ", Not Done");
            }
        }
    }
    class MyThread extends Thread
    {
        public MyThread()
        {
            super();
        }
        public void run()
        {
            int i = 0;
            while (statusDialog != null && statusDialog.getButtonClicked() != StatusDialog.STOP)
            {
                final StatusDialog fsd = statusDialog;
                final int fi = i;
                try {
                    SwingUtilities.invokeAndWait( new Runnable() {
                        public void run() {
                            fsd.addDetail("copying file" + fi +", Not Done, Unknown");
                            fsd.setProgressText("copying file" + fi);
                        }
                    });
                }
                catch (Exception e) {
                    System.err.println(e);
                }
                
                i++;
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException e) 
                {
                    //Debug.println("sleep exception");
                }
            }
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
        StatusDialogTest parent = new StatusDialogTest();
        parent.setTitle("Test");       
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