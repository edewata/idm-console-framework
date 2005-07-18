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
package supermail.configuration;

import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.border.*;
import com.netscape.management.client.*;
import com.netscape.management.client.util.*;
import com.netscape.management.client.console.ConsoleInfo;
import com.netscape.management.nmclf.SuiConstants;
import com.netscape.management.nmclf.SuiOptionPane;

/**
 * Inplementation for the Configuration Node right-hand panel.
 * The panel is shown when the node is selected.
 * This class illistrates the following techniqes:
 * 1) How to crate a panel with a proper spacing (see create*() methods)
 * 2) How to externelize a class and use ResourceSet for externelized strings (_i18n)
 * 3) How to interact with the Framework status bar (see setStatusBusy())
 *
 */
public class OperationNodeRHP extends JPanel implements SuiConstants
{
    static ResourceSet _resource;
    static String _i18nStartDescription, _i18nStopDescription;
    static String _i18nStart, _i18nStop, _i18nHelp;
    static String _i18nServerStarted, _i18nServerStopped;
    
    static {
        _resource = new ResourceSet("supermail.supermail");
        _i18nStartDescription =_resource.getString("op", "StartDescription");
        _i18nStopDescription =_resource.getString("op", "StopDescription");
        _i18nStart = _resource.getString("op", "Start");
        _i18nStop = _resource.getString("op", "Stop");
        _i18nServerStarted = _resource.getString("op", "ServerStarted");
        _i18nServerStopped = _resource.getString("op", "ServerStopped");
    }    
    
    ConsoleInfo _consoleInfo;
    JButton _btnStart, _btnStop;

    /**
     * Constructor
     */
	public OperationNodeRHP(ConsoleInfo consoleInfo)
	{
	    _consoleInfo = consoleInfo;
	    createPanelLayout();
    	}


    /**
     * Layout the panel content
     */
    private void createPanelLayout() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(COMPONENT_SPACE, COMPONENT_SPACE, COMPONENT_SPACE, COMPONENT_SPACE));
        add(createStartStopPanel(), BorderLayout.NORTH);
    }    


    /**
     * Create a subpanel consisting of start/stop button and instruction text
     */
    private JPanel createStartStopPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        int row=0;

		MultilineLabel mlLabel = new MultilineLabel(_i18nStartDescription, 2, 50);
        gbc.insets     = new Insets(SEPARATED_COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 2;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        p.add(mlLabel, gbc);
        row++;

        _btnStart = new JButton(_i18nStart);
        _btnStart.addActionListener(_cmdListener);
        gbc.insets     = new Insets(COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        p.add(_btnStart, gbc);

		JLabel spacer = new JLabel(" ");
        gbc.insets     = new Insets(COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 1;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.HORIZONTAL;
        p.add(spacer, gbc);
        row++;

		mlLabel = new MultilineLabel(_i18nStopDescription, 2, 50);
        gbc.insets     = new Insets(SEPARATED_COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 2;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        p.add(mlLabel, gbc);
        row++;


        _btnStop = new JButton(_i18nStop);
        _btnStop.addActionListener(_cmdListener);
        gbc.insets     = new Insets(COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 2;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        p.add(_btnStop, gbc);
        row++;

        return p;

    }

    
    /**
     * Dispatch button press event to appropriate methods
     */
    ActionListener _cmdListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _btnStart) {
                startServer();
            }
            else if (e.getSource() == _btnStop) {
                stopServer();
            }    
            System.err.println(e.getActionCommand());
        }
    };        

    /**
     * A simulation for the start server method. A real-life example would communicate with
     * the back-end to stop the server.
     */
    protected void startServer() {
        setBusyStatus(_i18nStart, true);        
        try { Thread.currentThread().sleep(2000); } catch (Exception e) {}
        setBusyStatus("", false);
        SuiOptionPane.showMessageDialog(this, _i18nServerStarted, _i18nStart, SuiOptionPane.INFORMATION_MESSAGE);
    }    

    /**
     * A simulation for the stop server method. A real-life example would communicate with
     * the back-end to stop the server.
     */
    protected void stopServer() {
        setBusyStatus(_i18nStop, true);        
        try { Thread.currentThread().sleep(2000); } catch (Exception e) {}
        setBusyStatus("", false);
        SuiOptionPane.showMessageDialog(this, _i18nServerStopped, _i18nStop, SuiOptionPane.INFORMATION_MESSAGE);
    }
    
 
    /**
     * Busy status indicators
     * Busy status is denoted with the change of cursor to "wait" , setting the state of 
     * the ProgressStatus component on the Framework status bar to "busy:, and setting the text
     * on the Framework status bar.
     */    
    private void setBusyStatus(String status, boolean isBusy) {

		JFrame frame = (JFrame)SwingUtilities.getAncestorOfClass(JFrame.class, this);
		ResourcePage page = (ResourcePage)SwingUtilities.getAncestorOfClass(ResourcePage.class, this);
		ResourceModel rpm = (ResourceModel)page.getModel();

		if (isBusy) {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			rpm.fireChangeStatusItemState(null, Framework.STATUS_TEXT, status);
			rpm.fireChangeStatusItemState(null, ResourcePage.STATUS_PROGRESS, StatusItemProgress.STATE_BUSY);
		}
		else {
			frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			rpm.fireChangeStatusItemState(null, Framework.STATUS_TEXT, status);
			rpm.fireChangeStatusItemState(null, ResourcePage.STATUS_PROGRESS, new Integer(0));
		}
	}	
}
