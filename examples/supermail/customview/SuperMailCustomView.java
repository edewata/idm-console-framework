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
package supermail.customview;

import java.awt.*;
import com.sun.java.swing.*;
import com.sun.java.swing.tree.*;
import netscape.ldap.*;

import com.netscape.management.client.*;
import com.netscape.management.client.topology.*;

public class SuperMailCustomView implements ICustomView
{
	ResourceModel _treeModel;
	ResourceObject _root;

	public SuperMailCustomView()
	{
	}

	public void initialize(LDAPConnection ldc, String customViewDN)
	{
		// initialize the model
		_root = new ResourceObject("Root");
		ResourceObject node1 = new ResourceObject("Node 1");
		ResourceObject node2 = new ResourceObject("Node 2");
		ResourceObject node3 = new ResourceObject("Node 3");
		ResourceObject subnode1 = new ResourceObject("Sub Node 1");
		ResourceObject subnode2 = new ResourceObject("Sub Node 2");
		ResourceObject subnode3 = new ResourceObject("Sub Node 3");

		_root.add(node1);
		node1.add(subnode1);
		_root.add(node2);
		node2.add(subnode2);
		_root.add(node3);
		node3.add(subnode3);

		_treeModel = new ResourceModel(_root);
	}

	public String getDisplayName()
	{
		return "Super Mail Servers";
	}

	public void setTreeModel(TreeModel newModel)
	{
		_treeModel = (ResourceModel)newModel;
	}

	public TreeModel getTreeModel()
	{
		return _treeModel;
	}
}
