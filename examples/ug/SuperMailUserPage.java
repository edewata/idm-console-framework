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

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.sun.java.swing.text.*;

import com.netscape.management.nmclf.SuiLookAndFeel;
import com.netscape.management.client.util.*;
import com.netscape.management.client.ug.*;

import netscape.ldap.*;



/**
 * SuperMailUserPage is an example customized plugin for the
 * ResourceEditor. It is used to edit information specific to
 * SuperMail users.
 *
 * All ResourceEditor plugins must implement IResourceEditorPage and
 * Observer interfaces. SuperMailUserPage also implements ChangeListener
 * and DocumentListener interfaces to keep track of modifications to the
 * page.
 */
public class SuperMailUserPage extends JPanel
                               implements IResourceEditorPage,
                                          Observer,
                                          ChangeListener,
                                          DocumentListener {

    // Specify property file without the trailing ".properties". Property
    // file contains externalized strings for i18n.
    static final ResourceSet _resource = new ResourceSet("supermailug");

    // Constants for the SuperMailUser object class attributes (see SuperMailSchema.conf).
    static final String ATTR_EMPLOYEE_NUMBER = "employeeNumber";
    static final String ATTR_ENABLE_AUTO_REPLY = "enableAutoReply";
    static final String ATTR_AUTO_REPLY_TEXT = "autoReplyText";

    static final String AUTO_REPLY_ENABLED = "on";
    static final String AUTO_REPLY_DISABLED = "off";

    ResourceEditor _parent;
    String _id;

    JTextField _employeeNumber;
    JCheckBox _enableAutoReply;
    JLabel  _autoReplyTextLabel;
    JTextArea  _autoReplyText;

    String _oldEmployeeNumber;
    boolean _oldEnableAutoReply;
    String _oldAutoReplyText;

    boolean _isModified = false;
    boolean _isReadOnly = false;
    boolean _isEnabled = true;


    /**
     * Constructor
     */
    public SuperMailUserPage() {
    }


	/**
     * Implements the Observer interface.
     * Updates the view for the page. If some other control modifies the
     * data displayed on this page, this method can be used to reflect
     * those changes on this page.
     *
     * @param o    the observable object
     * @param arg  the attribute to update
     */
	public void update(Observable o,
                       Object arg) {
    }


    /**
     * Implements the ChangeListener interface.
     * Updates the modified flag.
     *
     * @param e  change event
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == _enableAutoReply &&
            _enableAutoReply.isSelected() != _oldEnableAutoReply) {
            _isModified = true;
        }
    }


    /**
     * Implements the DocumentListener interface.
     * Updates the modified flag.
     *
     * @param e  document update event
     */
    public void insertUpdate(DocumentEvent e) {
        documentChanged(e);
    }


    /**
     * Implements the DocumentListener interface.
     * Updates the modified flag.
     *
     * @param e  document update event
     */
    public void removeUpdate(DocumentEvent e) {
        documentChanged(e);
    }

    /**
     * Implements the DocumentListener interface.
     * Updates the modified flag.
     *
     * @param e  document update event
     */
    public void changedUpdate(DocumentEvent e) {
        documentChanged(e);
    }


    /**
     * Updates the modified flag.
     *
     * @param e  document update event
     */
    private void documentChanged(DocumentEvent e) {
        if (e.getDocument() == _employeeNumber &&
            _employeeNumber.getText().equals(_oldEmployeeNumber) == false) {
            _isModified = true;
        }
        else if (e.getDocument() == _autoReplyText &&
                 _autoReplyText.getText().equals(_oldAutoReplyText) == false) {
            _isModified = true;
        }
    }


    /**
     * Implements the IResourceEditorPage interface.
     * Initializes the page with context information. It will be called once
     * the page is added to resource editor.
     *
     * @param observable  the observable object
     * @param parent      the resource editor container
     */
    public void initialize(ResourcePageObservable observable,
                           ResourceEditor parent) {
        _parent = parent;
        _id = _resource.getString("userPage", "id");  // Retrieve externalized strings from the property file.

        JLabel employeeNumberLabel = new JLabel(_resource.getString("userPage", "employeeNumber"), SwingConstants.RIGHT);
        _employeeNumber = new JTextField(10);
        _employeeNumber.getDocument().addDocumentListener(this);

        _enableAutoReply = new JCheckBox(_resource.getString("userPage", "enableAutoReply"));
        _enableAutoReply.addChangeListener(this);

        _autoReplyTextLabel = new JLabel(_resource.getString("userPage", "autoReplyText"), SwingConstants.RIGHT);
        _autoReplyText = new UGTextArea(); // UGTextArea extends JTextArea.
        _autoReplyText.getDocument().addDocumentListener(this);

        // Used for alignment. Blank label expands to take up the empty space.
        JLabel blankLabel = new JLabel("");

        // Layout widgets
        JPanel p = new JPanel(new GridBagLayout());
        GridBagUtil.constrain(p, employeeNumberLabel,
                              0, 0,
                              1, 1, 0.0, 0.0,
                              GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                              SuiLookAndFeel.VERT_WINDOW_INSET, SuiLookAndFeel.HORIZ_WINDOW_INSET, 0, 0);
        GridBagUtil.constrain(p, _employeeNumber,
                              1, 0,
                              1, 1, 0.0, 0.0,
                              GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                              SuiLookAndFeel.VERT_WINDOW_INSET, SuiLookAndFeel.DIFFERENT_COMPONENT_SPACE,
                              0, SuiLookAndFeel.HORIZ_WINDOW_INSET);

        GridBagUtil.constrain(p, _enableAutoReply,
                              1, 1,
                              GridBagConstraints.REMAINDER, 1, 1.0, 0.0,
                              GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                              SuiLookAndFeel.SEPARATED_COMPONENT_SPACE, SuiLookAndFeel.HORIZ_WINDOW_INSET, 0, 0);

        GridBagUtil.constrain(p, _autoReplyTextLabel,
                              0, 2,
                              1, 1, 0.0, 0.0,
                              GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                              SuiLookAndFeel.COMPONENT_SPACE, SuiLookAndFeel.HORIZ_WINDOW_INSET, 0, 0);
        GridBagUtil.constrain(p, _autoReplyText,
                              1, 2,
                              1, 1, 0.0, 0.0,
                              GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
                              SuiLookAndFeel.COMPONENT_SPACE, SuiLookAndFeel.DIFFERENT_COMPONENT_SPACE,
                              0, SuiLookAndFeel.HORIZ_WINDOW_INSET);

        GridBagUtil.constrain(p, blankLabel,
                              0, 3,
                              GridBagConstraints.REMAINDER, GridBagConstraints.REMAINDER, 1.0, 1.0,
                              GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                              SuiLookAndFeel.COMPONENT_SPACE, SuiLookAndFeel.HORIZ_WINDOW_INSET,
                              SuiLookAndFeel.VERT_WINDOW_INSET, SuiLookAndFeel.HORIZ_WINDOW_INSET);

        // Auto reply text area can grow to multiple lines. The scroll pane
        // lets the text area grow and provide the means to scroll to view the
        // rest of the JPanel.
        JScrollPane sp = new JScrollPane(p);
        sp.setBorder(null);

        setLayout(new BorderLayout());
        add("Center", sp);

        // Initialize fields
        _oldEmployeeNumber = observable.get(ATTR_EMPLOYEE_NUMBER, 0);
        _employeeNumber.setText(_oldEmployeeNumber);

        String value = observable.get(ATTR_ENABLE_AUTO_REPLY, 0);
        if (value.equalsIgnoreCase(AUTO_REPLY_ENABLED)) {
            _oldEnableAutoReply = true;
        }
        else {
            _oldEnableAutoReply = false;
        }
        _enableAutoReply.setSelected(_oldEnableAutoReply);

        _oldAutoReplyText = observable.get(ATTR_AUTO_REPLY_TEXT, 0);
        _autoReplyText.setText(_oldAutoReplyText);
    }


    /**
     * Implements the IResourceEditorPage interface.
     * Returns unique ID string which identifies the page.
     *
     * @return  unique ID for the page
     */
    public String getID() {
        return _id;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Handle some post save condition. This is called after the
     * information is saved and the object has been created in
     * the directory server.
     *
     * @param observable     the observable object
     * @return               true if save succeeded; false otherwise
     * @exception Exception
     */
    public boolean afterSave(ResourcePageObservable observable) throws Exception {
        return true;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Saves all modified information to the observable object
     *
     * @param observable     the observable object
     * @return               true if save succeeded; false otherwise
     * @exception Exception
     */
    public boolean save(ResourcePageObservable observable) throws Exception {
        // Compare to old value, and if different, save or delete.
        // Trim leading and trailing white space characters.
        if (_employeeNumber.getText().equals(_oldEmployeeNumber) == false) {
            if (_employeeNumber.getText().trim().length() == 0) {
                observable.delete(ATTR_EMPLOYEE_NUMBER, _oldEmployeeNumber);
                _oldEmployeeNumber = "";
            }
            else {
                String newEmployeeNumber = _employeeNumber.getText().trim();
                observable.replace(ATTR_EMPLOYEE_NUMBER, newEmployeeNumber);
                _oldEmployeeNumber = newEmployeeNumber;
            }
        }

        if (_enableAutoReply.isSelected() != _oldEnableAutoReply) {
            if (_enableAutoReply.isSelected()) {
                observable.replace(ATTR_ENABLE_AUTO_REPLY, AUTO_REPLY_ENABLED);
            }
            else {
                observable.replace(ATTR_ENABLE_AUTO_REPLY, AUTO_REPLY_DISABLED);
            }
            _oldEnableAutoReply = _enableAutoReply.isSelected();
        }

        if (_autoReplyText.getText().equals(_oldAutoReplyText) == false) {
            if (_autoReplyText.getText().trim().length() == 0) {
                observable.delete(ATTR_AUTO_REPLY_TEXT, _oldAutoReplyText);
                _oldAutoReplyText = "";
            }
            else {
                String newAutoReplyText = _autoReplyText.getText().trim();
                observable.replace(ATTR_AUTO_REPLY_TEXT, newAutoReplyText);
                _oldAutoReplyText = newAutoReplyText;
            }
        }

        return true;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Clears all information on the page.
     */
    public void clear() {
        _employeeNumber.setText("");
        _enableAutoReply.setSelected(false);
        _autoReplyText.setText("");
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Resets information on the page.
     */
    public void reset() {
        _employeeNumber.setText(_oldEmployeeNumber);
        _enableAutoReply.setSelected(_oldEnableAutoReply);
        _autoReplyText.setText(_oldAutoReplyText);
        _isModified = false;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Sets default information on the page.
     */
    public void setDefault() {
        _employeeNumber.setText("");
        _enableAutoReply.setSelected(false);
        _autoReplyText.setText("");
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Specifies whether any information on the page has been modified.
     *
     * @return  true if some information has been modified; false otherwise
     */
    public boolean isModified() {
        return _isModified;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Sets the modified flag for the page.
     *
     * @param value  true or false
     */
    public void setModified(boolean value) {
        _isModified = value;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Specifies whether the information on the page is read only.
     *
     * @return  true if some information has been modified; false otherwise
     */
    public boolean isReadOnly() {
        return _isReadOnly;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Sets the read only flag for the page.
     *
     * @param value  true or false
     */
    public void setReadOnly(boolean value) {
        _isReadOnly = value;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Sets the enabled flag for the page.
     *
     * @param value  true or false
     */
    public void setEnable(boolean value) {
        _isEnabled = value;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Specifies whether all required information has been provided for
     * the page.
     *
     * @return  true if all required information has been provided; false otherwise
     */
    public boolean isComplete() {
        return true; // None of the information on this page required.
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Returns a brief name for the page. The name should reflect the
     * plugin page.
     */
    public String getDisplayName() {
        return _id;
    }


    /**
     * Implements the IResourceEditorPage interface. 
     * Displays help information for the page.
     */
    public void help() {
        Help help =  new Help(_resource);
        if (help != null) {
            // Start the help page for the resource file help token
            help.help("userPage", "help");
        }
        else {
            System.out.println("Help not implemented for SuperMailUserPage");
        }
    }
}
