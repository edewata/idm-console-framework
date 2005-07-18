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
import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.border.*;
import com.netscape.management.client.*;
import com.netscape.management.client.util.*;
import com.netscape.management.client.ug.*;
import com.netscape.management.client.console.ConsoleInfo;
import com.netscape.management.nmclf.SuiConstants;
import com.netscape.management.nmclf.SuiOptionPane;
import netscape.ldap.*;


/**
 * Inplementation for the Configuration Node right-hand panel.
 * The panel is shown when the node is selected.
 *
 * This class illistrates the following techniqes:
 * 1) How to crate a panel with a proper spacing (see create*() methods)
 * 2) How to externelize a class and use ResourceSet for externelized strings (_i18n)
 * 3) How to interact with the Framework status bar (see setStatusBusy())
 * 4) How to use help system for Netscape Servers (showHelp) and non-Netscape servers (showHelp2)
 *
 */
public class ConfigurationNodeRHP extends JPanel implements SuiConstants
{
    static ResourceSet _resource;
    static String _i18nPortGroup, _i18nAdminGroup;
    static String _i18nPortDescription, _i18nAdminDescription;
    static String _i18nSMTPPort, _i18nPOP3Port, _i18nDefaultAdmin, _i18nAdminBrowse;
    static String _i18nSave, _i18nReset, _i18nHelp, _i18nError, _i18nDataSaved;
    static String _i18nErrStartPicker;
    
    static {
        _resource = new ResourceSet("supermail.supermail");
        _i18nPortGroup = _resource.getString("config", "PortGroup");
        _i18nAdminGroup = _resource.getString("config","AdminGroup");
        _i18nPortDescription = _resource.getString("config","PortDescription");
        _i18nAdminDescription =_resource.getString("config","AdminDescription");
        _i18nPOP3Port = _resource.getString("config","POP3Port");
        _i18nSMTPPort = _resource.getString("config","SMTPPort");
        _i18nDefaultAdmin = _resource.getString("config","DefaultAdmin");
        _i18nAdminBrowse = _resource.getString("config","AdminBrowse");
        _i18nSave = _resource.getString("config","Save");
        _i18nReset = _resource.getString("config","Reset");
        _i18nHelp = _resource.getString("config","Help");
        _i18nError = _resource.getString("config", "Error"); 
        _i18nDataSaved = _resource.getString("config","DataSaved");
        _i18nErrStartPicker=_resource.getString("config","ErrStartPicker");
    }    
    
    ConsoleInfo _consoleInfo;
    JTextField _txtPOP3Port, _txtSMTPPort, _txtDefaultAdmin;
    JButton _btnAdminBrowse, _btnSave, _btnReset, _btnHelp;
    
    /**
     * Panel constructor
     */
	public ConfigurationNodeRHP(ConsoleInfo consoleInfo)
	{
	    _consoleInfo = consoleInfo;
	    createPanelLayout();
	    resetPanel();
    }

    /**
     * Reset panel content to default values
     */
    protected void resetPanel() {
        _txtSMTPPort.setText("25");
        _txtPOP3Port.setText("110");
        _txtDefaultAdmin.setText("admin");
    }    

    /**
     * Create the panel layout for the input panel consisting of input panel and
     * command button bar. Command button bar is anchored to the bottom of the page.
     * Notice the spacing
     */

    private void createPanelLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets     = new Insets(0,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = 0;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 1.0;
        gbc.anchor     = gbc.NORTH;
        gbc.fill       = gbc.HORIZONTAL;
        add(createInputPanel(), gbc);

        gbc.insets     = new Insets(DIFFERENT_COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = 1;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.SOUTH;
        gbc.fill       = gbc.HORIZONTAL;
        add(createCommandButtons(), gbc);
    }

    /**
     * Create the layout for the input panel consisting of Port Selection Group and
     * Defaul Administrator group
     * Notice the spacing
     */

    private JPanel createInputPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel ctrlGroup;

        gbc.insets     = new Insets(0,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = 0;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.HORIZONTAL;
        p.add(createPortGroup(), gbc);

        gbc.insets     = new Insets(DIFFERENT_COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = 1;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.HORIZONTAL;
        p.add(createAdministratorGroup(), gbc);

        return p;
    }

    /**
     * Create the layout for the port selection group
     * Notice the spacing
     */
    private JPanel createPortGroup() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel group = new JPanel(new GridBagLayout());
		group.setBorder(createGroupBorder(_i18nPortGroup));
		int row=0;

		MultilineLabel mlLabel = new MultilineLabel(_i18nPortDescription, 3, 50);
        gbc.insets     = new Insets(0,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 2;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        group.add(mlLabel, gbc);
        row++;

		JLabel label = new JLabel(_i18nSMTPPort);
        gbc.insets     = new Insets(COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 0.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.EAST;
        gbc.fill       = gbc.NONE;
        group.add(label, gbc);

        _txtSMTPPort = new JTextField(8);
        gbc.insets     = new Insets(COMPONENT_SPACE,DIFFERENT_COMPONENT_SPACE,0,0);
        gbc.gridx      = 1;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        group.add(_txtSMTPPort, gbc);
        row++;
        
		label = new JLabel(_i18nPOP3Port);
        gbc.insets     = new Insets(COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 0.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.EAST;
        gbc.fill       = gbc.NONE;
        group.add(label, gbc);

        _txtPOP3Port = new JTextField(8);
        gbc.insets     = new Insets(COMPONENT_SPACE,DIFFERENT_COMPONENT_SPACE,0,0);
        gbc.gridx      = 1;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        group.add(_txtPOP3Port, gbc);
        row++;

        return group;
    }


    /**
     * Create a layout for the Defaul Administrator group
     * Notice the spacing     
     */
    private JPanel createAdministratorGroup() {
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel group = new JPanel(new GridBagLayout());
		group.setBorder(createGroupBorder(_i18nAdminGroup));
		int row=0;

		MultilineLabel mlLabel = new MultilineLabel(_i18nAdminDescription, 2, 50);
        gbc.insets     = new Insets(0, 0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 3;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.NONE;
        group.add(mlLabel, gbc);
        row++;

		JLabel label = new JLabel(_i18nDefaultAdmin);
        gbc.insets     = new Insets(COMPONENT_SPACE,0,0,0);
        gbc.gridx      = 0;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 0.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.EAST;
        gbc.fill       = gbc.NONE;
        group.add(label, gbc);

        _txtDefaultAdmin = new JTextField(22);
        gbc.insets     = new Insets(COMPONENT_SPACE,DIFFERENT_COMPONENT_SPACE,0,0);
        gbc.gridx      = 1;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 1.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.WEST;
        gbc.fill       = gbc.HORIZONTAL;
        group.add(_txtDefaultAdmin, gbc);
        
		_btnAdminBrowse = new JButton(_i18nAdminBrowse);
		_btnAdminBrowse.addActionListener(_cmdListener);
        gbc.insets     = new Insets(COMPONENT_SPACE,DIFFERENT_COMPONENT_SPACE,0,0);
        gbc.gridx      = 2;
        gbc.gridy      = row;
        gbc.gridwidth  = 1;
        gbc.gridheight = 1;
        gbc.weightx    = 0.0;
        gbc.weighty    = 0.0;
        gbc.anchor     = gbc.EAST;
        gbc.fill       = gbc.NONE;
        group.add(_btnAdminBrowse, gbc);
        row++;

        return group;
    }


    /**
     * Create the command button bar: Save, Reset, Help
     * Notice the spacing between buttons. Save and Reset are related buttons and COMPONENT_SPACE
     * is used between them. Help button is not related to them and slightly bigger
     * SEPARATED_COMPONENT_SPACE is used. See Console UI guidlines for more details
     */
    private JPanel createCommandButtons() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        _btnSave = JButtonFactory.create(_i18nSave);
        _btnSave.addActionListener(_cmdListener);
        gbc.insets     = new Insets(COMPONENT_SPACE,0,COMPONENT_SPACE,0);
        gbc.gridx      = 0;
        buttons.add(_btnSave, gbc);

        _btnReset = JButtonFactory.create(_i18nReset);
        _btnReset.addActionListener(_cmdListener);
        gbc.insets     = new Insets(COMPONENT_SPACE,COMPONENT_SPACE,COMPONENT_SPACE,0);
        gbc.gridx      = 1;
        buttons.add(_btnReset, gbc);

        _btnHelp = JButtonFactory.create(_i18nHelp);
        _btnHelp.addActionListener(_cmdListener);
        gbc.insets     = new Insets(COMPONENT_SPACE,SEPARATED_COMPONENT_SPACE,COMPONENT_SPACE,0);
        gbc.gridx      = 2;
        buttons.add(_btnHelp, gbc);
        
        JButtonFactory.resize(_btnSave, new JButton[] { _btnReset, _btnHelp });
        p.add(buttons, BorderLayout.EAST);
        return p;
    }
        

    /**
     * Dispatch button press event to appropriate methods
     */
    ActionListener _cmdListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == _btnAdminBrowse) {
                selectDefaultAdmin();
            }    
            else if (e.getSource() == _btnSave) {
                saveData();
            }    
            else if (e.getSource() == _btnReset) {
                resetPanel();
            }    
            else if (e.getSource() == _btnHelp) {
                showHelp();
            }    

            System.err.println(e.getActionCommand());
        }
    };
    
    /**
     * Open ResourcePicker to select default admin user dn
     * The ResourcePicker requires the client to implement IRPCallBack interface. We use this
     * interface to set the _txtDefaultAdmin to the selected value.
     * Note: This method will fail if ConsoleInfo is not properly initialized, as ResourcePicker
     * depends on LDAP parameters in ConsoleInfo.
     */
    protected void selectDefaultAdmin() {
		ResourcePickerDlg dlg = null;
		setBusyStatus("Initializing ResourcePicker ...", true);
		try {
			dlg = new ResourcePickerDlg(_consoleInfo, new IRPCallBack() {
                public void getResults(Vector result) {
                    LDAPEntry entry = null;
		            if (result.size() > 0 && result.elementAt(0) instanceof LDAPEntry) {
			            entry = (LDAPEntry)result.elementAt(0);
			            if (entry!=null) { 
				        _txtDefaultAdmin.setText(entry.getDN());
			            }
		            }
		        }    
		    });		    
			dlg.setAllowChangeDirectory(true);
			setBusyStatus("", false);
			dlg.show();
		}
		catch (Exception e) {
		    setBusyStatus("", false);
		    SuiOptionPane.showMessageDialog(this, _i18nErrStartPicker, _i18nError, SuiOptionPane.ERROR_MESSAGE);
		}
		finally {
		    if (dlg != null) {
			    dlg.dispose();
			}    
		}
    }
    
    /**
     * Simulate save data operation
     * Data validation and interaction with the back-end are missing here.
     * Status indivators are used to provide user feedback.
     */
    protected void saveData() {
        setBusyStatus("Saving the data...", true);
        try { Thread.currentThread().sleep(2000); } catch (Exception e) {}
        setBusyStatus("", false);
        SuiOptionPane.showMessageDialog(this, _i18nDataSaved, _i18nSave, SuiOptionPane.INFORMATION_MESSAGE);
    }    

    /**
     * Show help for Netscape Server Product.
     * Help pages are expected to be installed in a netscape server root. The help page is
     * retrieved using the Admin Server help CGI
     */
    protected void showHelp() {
        Help help =  new Help(_resource);
        // Start the help page for the resource file help token "configHelp". The help token 
        // value should be the name of the product, "supermail" in this case.
        help.help("configHelp");
    }

    /**
     * Show a help page in a browser
     * Non-Netscape servers can show a help page, or in general case any html page, 
     * by starting the browser and opening the url for the page.
     */
    protected void showHelp2() {
	    Browser browser = new Browser("Help");
	    String url = "http://host:port/SuperMailHelp.html";
	    boolean res = browser.open(url, Browser.NEW_WINDOW);
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

    /**
     * Create border for a group of related UI components using the proper spacing
     */
    private Border createGroupBorder(String label) {
        TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),label);
		Border interiorBorder = BorderFactory.createEmptyBorder(COMPONENT_SPACE, COMPONENT_SPACE, COMPONENT_SPACE, COMPONENT_SPACE);
		return BorderFactory.createCompoundBorder(titleBorder, interiorBorder);
	}
}