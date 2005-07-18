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
 * Operation Node model
 * This is a minimal definition of a node. The node extends ResourceObject only to provide a
 * custom RHP. For example of how to define node context menus see SuperMailServerNode.java
 */

public class OperationNode extends ResourceObject
{
	private JPanel _configPanel;
	private ConsoleInfo _consoleInfo;
	
    /**
	 * Node Constractor
	 * It is a common practice to pass ConsoleInfo to the constructor as a parameter, so that
	 * LDAP and HTTP requests can be handled from the node. (For the full list of properties in
	 * ConsoleInfo see ConsoleInfo class). This example does not use ConsoleInfo, because 
     * it does not interact with the back-end servers.
	 */
    public OperationNode(ConsoleInfo consoleInfo) {
        super("Operation");
        _consoleInfo = consoleInfo;
        setAllowsChildren(false);
    }

    /**
     * Override getIcon() to specify an icon for this node. If the returned value is null,
     * or the method is not defined, the node does not have an icon.
     */
    public Icon getIcon() {
        return new ImageIcon("images/panel.gif");
    }
    
	/**
     * Called when the node is selected to get RHP
	 */    
	public Component getCustomPanel()
	{
		return new OperationNodeRHP(_consoleInfo);
    }
}
