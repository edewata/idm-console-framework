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
package supermail.topology;

import java.awt.*;
import java.util.*;
import com.sun.java.swing.*;
import com.sun.java.swing.tree.*;
import netscape.ldap.*;

import com.netscape.management.client.*;
import com.netscape.management.client.console.*;
import com.netscape.management.client.topology.*;

public class SuperMailTopologyPlugin implements ITopologyPlugin
{
	ResourceObject _root;
	Vector				 _vRoot;
	ResourceObject node1;
	ResourceObject node2;
	ResourceObject node3;

	public SuperMailTopologyPlugin()
	{
	}

	public void initialize(ConsoleInfo info)
	{
		// initialize the model
		_root = new ResourceObject("Super Mail");
		node1 = new ResourceObject("Server Instance 1");
		node2 = new ResourceObject("Server Instance 2");
		node3 = new ResourceObject("Server Instance 3");

		_root.add(node1);
		_root.add(node2);
		_root.add(node3);

		_vRoot = new Vector();
		_vRoot.addElement(_root);
	}

	public Vector getTopNodes()
	{
		return null;
	}

	public Vector getAdditionalChildren(ResourceObject t)
	{
		if(t instanceof DomainNode)
		{
			if(((DomainNode)t).getDN().equalsIgnoreCase("ou=mcom.com, o=Netscaperoot"))
			{
				return _vRoot;
			}
		}
		return null;
	}

	public String getIDByResourceObject(ResourceObject res)
	{
		return res.toString();
	}

	public ResourceObject getResourceObjectByID(String id)
	{
		if(id.equals(_root.toString()))
		{
			return _root;
		}else if(id.equals(node1.toString()))
		{
			return node1;
		}else if(id.equals(node2.toString()))
		{
			return node2;
		}else if(id.equals(node3.toString()))
		{
			return node3;
		}
		return null;
	}
}

