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
import com.netscape.management.client.*;
import com.netscape.management.client.console.*;
import com.netscape.management.client.util.*;
import com.netscape.management.nmclf.*;
import netscape.ldap.*;

/**
 * Configuration Page definition
 * We reuse ResourcePage "as is", and only provide a custom page ResourceModel
 */
public class ConfigurationPage extends ResourcePage
{
	public ConfigurationPage(ConsoleInfo consoleInfo)
	{
		super(new PageResourceModel(consoleInfo));
	}
}

/**
 * Configuration Page Resource Model
 * Resource Model specifies resource tree node hierachy and page common menu items 
 */
class PageResourceModel extends ResourceModel implements IMenuInfo
{

        private static final String MENU_FILEOP1 = "file1";
        private static final String MENU_FILEOP2 = "file2";
        private static final String MENU_PAGECOMMON = "common";        
        
        IResourceObject[] _selection;
        ConsoleInfo _consoleInfo;

        /**
         * constrcutor and initialize all the internal variables
         */
        public PageResourceModel(ConsoleInfo consoleInfo) {

                _consoleInfo = consoleInfo;
                
                /**
                 * Define the resource tree. It has a root and two sub nodes
                 */
                ResourceObject root= new SuperMailServerNode(consoleInfo);
                root.add(new ConfigurationNode(consoleInfo));
                root.add(new OperationNode(consoleInfo));
                setRoot(root);
        }


	   /**
       * Implements IMenuInfo
       * Called when the page is selected to specify menu categores that require custom menu options
       * In this example we are extending File menu.
	   */
        public String[] getMenuCategoryIDs()
        {
                return new String[]
                {
                    Framework.MENU_FILE,
                    ResourcePage.MENU_OBJECT,
                    ResourcePage.MENU_CONTEXT
                };
        }

	    /**
        * Implements IMenuInfo
        * Called when a menu specified in getMenuCategoryIDs() is to be opened
        * Context and Object menu, by default, have the same menu items.
        *
        * Noticed that Context and Object menus contain the same item, however, we are creating a separate
        * instance for each item. This is bacause JFC menus are Container objects, and in AWT/JFC
        * when a component is added to a container, it is removed from the current container should
        * there be one. Thus, if we try to "reuse" the same menu item inside two or more menus, the
        * menu item will show up only in the last menu it was added to.        
        */
        public IMenuItem[] getMenuItems(String categoryID)
        {	    
                if(categoryID.equals(Framework.MENU_FILE))
                {
                    return new IMenuItem[]
                    {
				        new MenuItemText(MENU_FILEOP1,"FileOption1", "TODO:description"),
				        new MenuItemText(MENU_FILEOP2,"FileOption2", "TODO:description"),				        
                    };
                }
                else if(categoryID.equals(ResourcePage.MENU_OBJECT))
                {
                    return new IMenuItem[]
                    {
				        new MenuItemText(MENU_PAGECOMMON,"CommonContextMenuOption", "TODO:description"),
				        new MenuItemSeparator()
                    };
                }
                else if(categoryID.equals(ResourcePage.MENU_CONTEXT))
                {
                    System.err.println("Context");
                    return new IMenuItem[]
                    {
				        new MenuItemText(MENU_PAGECOMMON,"CommonContextMenuOption", "TODO:description"),
				        new MenuItemSeparator()
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
                if(item.getID().equals(MENU_FILEOP1))
                {
                    SuiOptionPane.showMessageDialog((ResourcePage)page, "FileOption1 slected", "Menu Selection", SuiOptionPane.INFORMATION_MESSAGE);
                }
                else if(item.getID().equals(MENU_FILEOP2))
                {
                    SuiOptionPane.showMessageDialog((ResourcePage)page, "FileOption2 slected", "Menu Selection", SuiOptionPane.INFORMATION_MESSAGE);
                }
                else if(item.getID().equals(MENU_PAGECOMMON))
                {
                    SuiOptionPane.showMessageDialog((ResourcePage)page, "Common Context option slected", "Menu Selection", SuiOptionPane.INFORMATION_MESSAGE);
                }
        }

}
