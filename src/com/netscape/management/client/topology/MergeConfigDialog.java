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
package com.netscape.management.client.topology;

import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.netscape.management.client.*;
import com.netscape.management.client.console.*;
import com.netscape.management.client.util.*;
import com.netscape.management.client.topology.ug.*;
import com.netscape.management.nmclf.*;
import netscape.ldap.*;
import netscape.ldap.util.*;
import java.io.*;

/**
 * Dialog for merging a server group to another Config DS
 *
 * @author   adam
 */
public class MergeConfigDialog extends AbstractDialog {
    private static ResourceSet _resource = new ResourceSet("com.netscape.management.client.topology.topology");
    private static String sMergeConfig = "MergeConfig";
    private static String sChangeDirectory = "ChangeDirectory";
    private Help _helpSession; // support for help.

    JTextField _dest_domain;
    JTextField _dest_host;
    JTextField _dest_port;
    JCheckBox _dest_ssl;
    JTextField _dest_binddn;
    SingleBytePasswordField _dest_bindpw;

    String _source_groupdn;
    ConsoleInfo _info;
    AdminGroupNode _agn;
    IPage _viewInstance;

    /**
     * constructor
     */
    public MergeConfigDialog(String serverGroupDN, ConsoleInfo info,
            AdminGroupNode agn, IPage viewInstance) {
        super(viewInstance.getFramework().getJFrame(),
                _resource.getString(sMergeConfig, "title"), true,
                OK | CANCEL | HELP);

        _helpSession = new Help(_resource);

        _source_groupdn = serverGroupDN;
        _info = info;
        _agn = agn;
        _viewInstance = viewInstance;

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagUtil.constrain(panel,
                new JLabel(
                _resource.getString(sMergeConfig, "MergeWarning"),
                JLabel.CENTER), 0, 0, GridBagConstraints.REMAINDER, 1,
                1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, 0, 0,
                SuiLookAndFeel.DIFFERENT_COMPONENT_SPACE, 0);

        JLabel lblDomain = new JLabel( _resource.getString(sMergeConfig, "DestDomain"),
                                       JLabel.RIGHT);
        GridBagUtil.constrain(panel,
                              lblDomain, 0, 1, 1, 1, 0.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiLookAndFeel.SEPARATED_COMPONENT_SPACE, 0, 0, 0);
        
        JLabel lblHost = new JLabel( _resource.getString(sMergeConfig, "DestHost"),
                                     JLabel.RIGHT);
        GridBagUtil.constrain(panel,
                              lblHost, 0, 2, 1, 1, 0.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiLookAndFeel.SEPARATED_COMPONENT_SPACE, 0, 0, 0);
        
        JLabel lblPort = new JLabel( _resource.getString(sMergeConfig, "DestPort"),
                                     JLabel.RIGHT);
        GridBagUtil.constrain(panel,
                              lblPort, 0, 3, 1, 1, 0.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiConstants.SEPARATED_COMPONENT_SPACE, 0, 0, 0);
        
        JLabel lblBindDn = new JLabel( _resource.getString(sMergeConfig, "DestBindDN"),
                                       JLabel.RIGHT);
        GridBagUtil.constrain(panel,
                              lblBindDn, 0, 5, 1, 1, 0.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiConstants.SEPARATED_COMPONENT_SPACE, 0, 0, 0);

        JLabel lblBindPassword = new JLabel( _resource.getString(sMergeConfig, "DestBindPW"),
                                             JLabel.RIGHT);
            GridBagUtil.constrain(panel,
                                  lblBindPassword, 0, 6, 1, 1, 0.0, 0.0,
                                  GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                                  SuiConstants.SEPARATED_COMPONENT_SPACE, 0, 0, 0);
            
        _dest_domain = new JTextField();
        lblDomain.setLabelFor(_dest_domain);
        GridBagUtil.constrain(panel, _dest_domain, 1, 1,
                              GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiConstants.SEPARATED_COMPONENT_SPACE,
                              SuiConstants.DIFFERENT_COMPONENT_SPACE, 0, 0);
        
        _dest_host = new JTextField();
        lblHost.setLabelFor(_dest_host);
        GridBagUtil.constrain(panel, _dest_host, 1, 2,
                              GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiConstants.SEPARATED_COMPONENT_SPACE,
                              SuiConstants.DIFFERENT_COMPONENT_SPACE, 0, 0);
        
        _dest_port = new JTextField(5);
        lblPort.setLabelFor(_dest_port);
        GridBagUtil.constrain(panel, _dest_port, 1, 3,
                              GridBagConstraints.RELATIVE, 1, 1.0, 0.0,
                              GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                              SuiConstants.SEPARATED_COMPONENT_SPACE,
                              SuiConstants.DIFFERENT_COMPONENT_SPACE, 0, 0);
        
        _dest_ssl =
            new JCheckBox(_resource.getString(sChangeDirectory, "ssl"));
        _dest_ssl.setHorizontalAlignment(_dest_ssl.RIGHT);
        GridBagUtil.constrain(panel, _dest_ssl, 2, 3, 1, 1, 1.0, 0.0,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                SuiConstants.SEPARATED_COMPONENT_SPACE,
                SuiConstants.DIFFERENT_COMPONENT_SPACE, 0, 0);

        _dest_binddn = new JTextField();
        lblBindDn.setLabelFor(_dest_binddn);
        GridBagUtil.constrain(panel, _dest_binddn, 1, 5,
                GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                SuiConstants.SEPARATED_COMPONENT_SPACE,
                SuiConstants.DIFFERENT_COMPONENT_SPACE, 0, 0);

        _dest_bindpw = new SingleBytePasswordField();
        lblBindPassword.setLabelFor(_dest_bindpw);
        GridBagUtil.constrain(panel, _dest_bindpw, 1, 6,
                GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                SuiConstants.SEPARATED_COMPONENT_SPACE,
                SuiConstants.DIFFERENT_COMPONENT_SPACE, 0, 0);

        setPanel(panel);
        setMinimumSize(470, 285);

        // Set default binddn/pwd
        _dest_binddn.setText(info.getAuthenticationDN());
        _dest_bindpw.setText(info.getAuthenticationPassword());

        // Set  default destination domain
        try {
            String[] dnAry = (new DN(serverGroupDN)).explodeDN(/*noTypes=*/true);
            String domain = dnAry[2].trim();
            _dest_domain.setText(domain);
        }
        catch (Exception e) {
            Debug.println("ERROR Set destination domain name " + e);
        }

    }

    /**
      * display help
      */
    public void helpInvoked() {
        _helpSession.contextHelp("topology", sMergeConfig);
    }

    /**
      * ok button is hit
      */
    protected void okInvoked() {
        setBusyCursor(true);

        String success_data = null;
        String queryString =
                new String(_info.getAdminURL() + "admin-serv/tasks/operation/MergeConfig?" +
                "source_groupdn=" +
                URLByteEncoder.encodeUTF8(_source_groupdn) +
                "&dest_domain=" +
                URLByteEncoder.encodeUTF8(_dest_domain.getText()) +
                "&dest_host=" +
                URLByteEncoder.encodeUTF8(_dest_host.getText()) +
                "&dest_port=" +
                URLByteEncoder.encodeUTF8(_dest_port.getText()) +
                "&dest_secure=" + (_dest_ssl.isSelected() ? "1" : "0") +
                "&dest_binddn=" +
                URLByteEncoder.encodeUTF8(_dest_binddn.getText()) +
                "&dest_bindpw=" +
                URLByteEncoder.encodeUTF8(_dest_bindpw.getText()));

        try {
            URL query = new URL(queryString);
            AdmTask task = new AdmTask(query, _info.getAuthenticationDN(),
                    _info.getAuthenticationPassword());
            task.setResponseTimeout(45); // sec

            int execStatus = task.exec();
            Hashtable retvals = task.getResult();
            Exception exception = task.getException();

            if (exception != null) {
                /* Failed */
                setBusyCursor(false);
                SuiOptionPane.showMessageDialog(
                        UtilConsoleGlobals.getActivatedFrame(),
                        exception.toString());
                return;
            } else if (retvals.get("NMC_ErrInfo") == null) {
                /* Success - store the data returned by mergeConfig */
                success_data = urlDecoder((String) retvals.get("NMC_Description"));
            } else {
                /* Failure - show the error dialog */
                setBusyCursor(false);
                SuiOptionPane.showMessageDialog(
                        UtilConsoleGlobals.getActivatedFrame(),
                        retvals.get("NMC_ErrInfo"));
                return;
            }
        } catch (MalformedURLException e) {
            System.out.println("ERROR MergeConfigDialog: Bad URL: " + e);
            setBusyCursor(false);
            return;
        } catch (Exception e) {
            System.out.println("ERROR MergeConfigDialog: exception: " + e);
            setBusyCursor(false);
            return;
        }

        String currentEntry, newSIE, newISIE;
        boolean errors = false;
        Vector serverIDs = _agn.getServerIDs();
        MergeThread [] mthreads = new MergeThread[serverIDs.size()];

        // call hooks for each server ID
        for (int count = 0; count < serverIDs.size(); count++) {

            newSIE = null;
            newISIE = null;
            mthreads[count] = null;

            // Note: all SIE's are returned before all ISIE's.
            StringTokenizer token = new StringTokenizer(success_data, "\n");
            while (token.hasMoreTokens()) {
                currentEntry = token.nextToken();
                if (currentEntry.startsWith("SIE:")) {
                    if (currentEntry.indexOf(
                            (String) serverIDs.elementAt(count)) != -1) {
                        // SIE match
                        newSIE = currentEntry.substring(4);
                    }
                } else if (currentEntry.startsWith("ISIE:")) {
                    if ((newSIE != null) &&
                            (newSIE.indexOf(currentEntry.substring(5))
                            != -1)) {
                        // ISIE match
                        newISIE = currentEntry.substring(5);
                    }
                }
            }

            if (newSIE == null || newISIE == null)// bad format; skip this server ID

                continue;

            // find the correct SIE and ISIE to pass into the instance's CGI
            queryString = new String(_info.getAdminURL() +
                    serverIDs.elementAt(count) +
                    "/tasks/configuration/DirectorySetup?op=setconfig&dsconfig.host=" +
                    URLByteEncoder.encodeUTF8(_dest_host.getText()) +
                    "&dsconfig.port=" +
                    URLByteEncoder.encodeUTF8(_dest_port.getText()) +
                    "&dsconfig.ssl=" +
                    URLByteEncoder.encodeUTF8(_dest_ssl.isSelected() ?
                    "true" : "false") + "&dsconfig.sieDN=" +
                    URLByteEncoder.encodeUTF8(newSIE) +
                    "&dsconfig.isieDN=" +
                    URLByteEncoder.encodeUTF8(newISIE));

            mthreads[count] = new MergeThread(_info, queryString);
            mthreads[count].start();

        } // processing server ID's

        // wait for all threads to die, see if any CGI's failed
        for (int count = 0; count < serverIDs.size(); count++) {
            if (mthreads[count] != null) {
                try {
                    mthreads[count].join();
                } catch (Exception e) {}
                if (mthreads[count].Failed() == true)
                    errors = true;
            }
        }

        setBusyCursor(false);
        if (errors == true)
            SuiOptionPane.showMessageDialog(
                    UtilConsoleGlobals.getActivatedFrame(),
                    _resource.getString(sMergeConfig, "UpdateFailure"));
        else {
            SuiOptionPane.showMessageDialog(
                    UtilConsoleGlobals.getActivatedFrame(),
                    _resource.getString(sMergeConfig, "Success"));
            super.okInvoked();
        }

    }

    public static String urlDecoder(String urlString) {
        ByteArrayOutputStream out =
                new ByteArrayOutputStream(urlString.length());
        for (int i = 0; i < urlString.length(); i++) {
            int c = (int) urlString.charAt(i);
            if (c == '+') {
                out.write(' ');
            } else if (c == '%') {
                int c1 = Character.digit(urlString.charAt(++i), 16);
                int c2 = Character.digit(urlString.charAt(++i), 16);
                out.write((char)(c1 * 16 + c2));
            } else {
                out.write(c);
            }
        }
        return out.toString();
    }

    class MergeThread extends Thread {

        private ConsoleInfo _info;
        private String _queryString;
        private boolean _errors;

        public MergeThread(ConsoleInfo info, String queryString) {
            _info = info;
            _queryString = queryString;
        }

        public void run() {

            try {
                URL query = new URL(_queryString);
                AdmTask task = new AdmTask(query,
                        _info.getAuthenticationDN(),
                        _info.getAuthenticationPassword());

                int execStatus = task.exec();
                Hashtable retvals = task.getResult();

                if (retvals.get("NMC_ErrInfo") != null)/* Failure */

                    _errors = true;
            } catch (Exception e) { }

        }

        public boolean Failed() {
            return _errors;
        }
    }



    // Used to temporarily grab input for JFrame glass pane, which disables
    // user input while a task thread is being run.
    private static KeyAdapter _tmpGrabKey = new KeyAdapter() {};
    private static MouseAdapter _tmpGrabMouse = new MouseAdapter() {};

    /**
    	* Grab or release user input.
    	*
    	* @param viewInstance  console view
    	* @param value         grab user input if true, release if false
    	*/
    private synchronized void setGrabAllInput(IPage viewInstance,
            boolean value) {
        JFrame frame = viewInstance.getFramework().getJFrame();
        Component glassPane = frame.getGlassPane();
        if (value) {
            glassPane.addKeyListener(_tmpGrabKey);
            glassPane.addMouseListener(_tmpGrabMouse);
            glassPane.setVisible(true);
        } else {
            glassPane.removeKeyListener(_tmpGrabKey);
            glassPane.removeMouseListener(_tmpGrabMouse);
            glassPane.setVisible(false);
        }
    }
}
