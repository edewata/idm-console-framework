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

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import com.sun.java.swing.tree.*;
import com.netscape.management.client.*;
import com.netscape.management.client.console.*;
import com.netscape.management.client.util.*;
import com.netscape.management.nmclf.*;
import netscape.ldap.*;


/**
 * A model for the top level node in the resource tree. The example illustrates the following
 * techniques:
 *
 * 1) How to spacify node label (see Constrctor)
 * 2) How to specify custom icon for the node (see getIcon())
 * 3) How to specify Context Menu and handle menu selection (see getMenu*(), actionMenu*())
 * 4) How to handle node selection and return RHP (see getCustomPanel())
 * 5) How to handle user double-click on the node (see run())
 *
 */
public class SuperMailServerNode extends ResourceObject implements IMenuInfo 
{
    public static ResourceSet _resource = new ResourceSet("supermail");
    public static Icon _icon = new RemoteImage("images/SuperMailServer.gif");

	static String MENU_CONFIG = "CONFIG";
	static String MENU_START = "START";
	static String MENU_STOP = "STOP";

	private JPanel _configPanel;
	private ConsoleInfo _consoleInfo;

	/**
	 * Node Constractor
	 * It is a common practice to pass ConsoleInfo to the constructor as a parameter, so that
	 * LDAP and HTTP requests can be handled from the node. (For the full list of properties in
	 * ConsoleInfo see ConsoleInfo class). This example does not use ConsoleInfo, because 
     * it does not interact with the back-end servers.
	 */
    public SuperMailServerNode(ConsoleInfo consoleInfo) {
        super("SuperMail Server");
        _consoleInfo = consoleInfo;
        setAllowsChildren(true);
    }

    /**
     * Specify the icon for the node
     */    
    public Icon getIcon() 
	{
        return _icon;
    }

	/**
     * Implements IMenuInfo
     * Called when the node is selected to specify menu categores that require custom menu options
     * In this example we are only defining Context menues: Node right-click (MENU_CONTEXT) and
     * Object menu on the frame menu Bar (MENU_OBJECT)
	 */
	public String[] getMenuCategoryIDs()
	{
		return new String[] 
		{
			ResourcePage.MENU_OBJECT,
			ResourcePage.MENU_CONTEXT
		};
	}

	/**
     * Implements IMenuInfo
     * Called when a menu specified in getMenuCategoryIDs() is to be opened
     *
     * Noticed that even though both menus contain the same items, we are creating a separate
     * instance for each item. This is bacause JFC menus are Container objects, and in AWT/JFC
     * when a component is added to a container, it is removed from the current container should
     * there be one. Thus, if we try to "reuse" the same menu item inside two or more menus, the
     * menu item will show up only in the last menu it was added to.
	 */
	public IMenuItem[] getMenuItems(String categoryID)
	{
        if((categoryID.equals(ResourcePage.MENU_OBJECT)) ||
           (categoryID.equals(ResourcePage.MENU_CONTEXT)))
		{
            return new IMenuItem[]
			{
				new MenuItemText(MENU_CONFIG, "Configure",/*description=*/"", /*enabled=*/true),
				new MenuItemSeparator(),				
				new MenuItemText(MENU_START,  "Start",    /*description=*/"", /*enabled=*/true),
				new MenuItemText(MENU_STOP,   "Stop",     /*description=*/"", /*enabled=*/true)
			};
        }
        return null;
	}

	/**
     * Implements IMenuInfo
     * Called when a menu item is selected in a menu
	 */
	public void actionMenuSelected(IPage page, IMenuItem item)
	{
		if(page instanceof ResourcePage)
		{
			if(item.getID().equals(MENU_CONFIG))
			{
				SuiOptionPane.showMessageDialog((ResourcePage)page, "Context menu Config selected", "Context menu selection", SuiOptionPane.INFORMATION_MESSAGE);
			}
			else if(item.getID().equals(MENU_START))
			{
				SuiOptionPane.showMessageDialog((ResourcePage)page, "Context menu Start selected", "Context menu selection", SuiOptionPane.INFORMATION_MESSAGE);
			}
			else if(item.getID().equals(MENU_STOP))
			{
				SuiOptionPane.showMessageDialog((ResourcePage)page, "Context menu Stop selected", "Context menu selection", SuiOptionPane.INFORMATION_MESSAGE);
			}
		
			else 
			{
				Debug.println("Not Yet Implemented: " + item);
			}
		}
	}

	/**
     * Called when the node is selected to get RHP
	 */
	public Component getCustomPanel()
	{
		return new SuperMailServerNodeRHP();
    }

	/**
     * Called when user double-clicks the node
	 */
	public boolean run(IPage page, IResourceObject selection[])
	{
		SuiOptionPane.showMessageDialog((ResourcePage)page, "Node double-clicked", "SuperMail Server", SuiOptionPane.INFORMATION_MESSAGE);
		return false;
	}
}
