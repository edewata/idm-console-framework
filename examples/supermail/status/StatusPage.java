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
package supermail.status;

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


public class StatusPage extends ResourcePage
{
	public StatusPage(ConsoleInfo ci)
	{
		super(new PageResourceModel(ci));
	}

	public String getPageTitle()
	{
		System.err.println(super.getPageTitle());
		return "Status";
	}
}

class PageResourceModel extends ResourceModel implements IMenuInfo
{
        static ResourceSet _resource = new ResourceSet("supermail");
        IResourceObject[] _selection;

        ConsoleInfo _consoleInfo;

        /**
         * constrcutor and initialize all the internal variables
         */
        public PageResourceModel(ConsoleInfo ci) 
		{
			_consoleInfo = ci;
			ResourceObject status = new ResourceObject("Status", new ImageIcon("images/SuperMailServer.gif"), null); // todo: need RHP
			ResourceObject logs = new LoggingNode();
			ResourceObject performance = new PerformanceNode();
			status.add(logs);
			status.add(performance);
			setRoot(status);
        }


      /**
       * Returns supported menu categories
       */
        public String[] getMenuCategoryIDs()
        {
                return new String[]
                {
                    Framework.MENU_FILE,
                    ResourcePage.MENU_CONTEXT
                };
        }

        public IMenuItem[] getMenuItems(String categoryID)
        {
	    
	    ResourceSet _resource = new ResourceSet("com.netscape.management.client.default");
                if(categoryID.equals(Framework.MENU_FILE))
                {
                        return new IMenuItem[]
                        {
				new MenuItemText("Menu1","Menu1", "TODO:description"),
				new MenuItemSeparator(),
                        };
                }
                return null;
        }

        public void actionObjectSelected(IPage viewInstance, IResourceObject[] selection, IResourceObject[] previousSelection)
        {
                super.actionObjectSelected(viewInstance, selection, previousSelection);
                _selection = selection;
        }

        /**
         * Notification that a menu item has been selected.
         */
        public void actionMenuSelected(IPage page, IMenuItem item)
        {
                if(item.getID().equals("Menu1"))
                {
                    SuiOptionPane.showMessageDialog((ResourcePage)page, "Menu1 slected", "Menu Selection", SuiOptionPane.INFORMATION_MESSAGE);
                }
                else if(item.getID().equals("Menu2"))
                {
                    //CertManagementDialog certmgt = new CertManagementDialog(_consoleInfo);
                }
        }

}
