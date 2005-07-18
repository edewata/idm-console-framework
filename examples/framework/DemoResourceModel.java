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
import com.netscape.management.client.*;
import com.netscape.management.client.util.*;

public class DemoResourceModel extends ResourceModel implements IMenuInfo
{
	static boolean _feedbackState = true;
    DemoResourceObject _root = null;
	boolean _isInitialized = false;
	DemoResourceObject _hosts;

    public DemoResourceModel()
	{
        if (_root == null)
		{
            _root = new DemoResourceObject("Console Root", new RemoteImage("icons/Topology16.gif"));

        }
    }

	private void initialize()
	{
		_isInitialized = true;

		_hosts = new DemoResourceObject("Engineering", new RemoteImage("icons/subnet16.gif"));
		DemoResourceObject gumby = new DemoResourceObject("Gumby", new RemoteImage("icons/host16.gif"));
		DemoResourceObject nightmare = new DemoResourceObject("Nightmare", new RemoteImage("icons/host16.gif"));
		DemoResourceObject dolby = new DemoResourceObject("Dolby", new RemoteImage("icons/host16.gif"));
		DemoResourceObject bluesky = new DemoResourceObject("Bluesky", new RemoteImage("icons/host16.gif"));
		DemoResourceObject skydome = new DemoResourceObject("Skydome", new RemoteImage("icons/host16.gif"));
		_hosts.add(gumby);
		_hosts.add(nightmare);
		_hosts.add(dolby);
		_hosts.add(bluesky);
		_hosts.add(skydome);
		_root.add(_hosts);

		DemoResourceObject usersgroups = new DemoResourceObject("Users and Groups");

		DemoResourceObject enterprise = new DemoResourceObject("Enterprise", new RemoteImage("icons/e16.gif"));
		DemoResourceObject messaging = new DemoResourceObject("Administration", new RemoteImage("icons/a16.gif"));
		DemoResourceObject directory = new DemoResourceObject("Directory", new RemoteImage("icons/d16.gif"));

		directory.add(usersgroups);

		DemoResourceObject performance = new DemoResourceObject("Performance");
		DemoResourceObject perfconn = new DemoResourceObject("Connection Statistics");
		DemoResourceObject perfnew = new DemoResourceObject("Add New Monitor");
		performance.add(perfconn);
		performance.add(perfnew);

		enterprise.add(performance);
		messaging.add(usersgroups);

		nightmare.add(enterprise);
		nightmare.add(messaging);
		nightmare.add(directory);

		DemoResourceObject users = new DemoResourceObject("Users");
		DemoResourceObject groups = new DemoResourceObject("Groups");
		usersgroups.add(users);
		usersgroups.add(groups);

		_root.add(new DynamicObject("Dynamic Nodes"));
	}

	public Object getRoot() 
	{
        return _root;
    }

    public Object getChild(Object parent, int index) 
	{
		if(!_isInitialized)
			initialize();

        if (parent instanceof DynamicObject) 
		{
            return (new DynamicObject("Recursive Node " + (index + 1)));
        }
        else 
		{
            return ((ResourceObject)parent).getChildAt(index);
        }
    }

    public int getChildCount(Object parent) 
	{
		if(!_isInitialized)
			initialize();

		if(parent instanceof DynamicObject)
		{
			return 4;
		}
		else
		{
			return ((ResourceObject)parent).getChildCount();
		}
    }

    public boolean isLeaf(Object node)
	{
		if(!_isInitialized)
			initialize();

		if(node instanceof DynamicObject)
		{
			return false;
		}
		else
		if(node instanceof ResourceObject)
		{
			return ((ResourceObject)node).getChildCount() == 0;
		}
		return false;
    }


	/**
	 * Returns menu categories for this model.
	 */
	public String[] getMenuCategoryIDs() 
	{
		return new String[] 
		{
			Framework.MENU_FILE, 
			ResourcePage.MENU_CONTEXT, 
			"ContextCategoryID",
			"FileCascadeID"
		};
	}

	/**
	 * Returns menu items for this model.
	 */
	public IMenuItem[] getMenuItems(String category) 
	{
		if (category.equals(ResourcePage.MENU_CONTEXT))
		{
            return new IMenuItem[]
			{
				new MenuItemCategory("ContextCategoryID", "MyCategory1"),
				new MenuItemSeparator(),
				new MenuItemText("MyContextItem1", "description1"),
				new MenuItemText("MyContextItem2", "description2"),
			};
		}
		else 
        if(category.equals(Framework.MENU_FILE))
		{
            return new IMenuItem[]
			{
				new MenuItemText("SelectHostsID", "Select Hosts Node", "description"),
				new MenuItemText("ExpandHostsID", "Expand Hosts Node", "description"),
				new MenuItemCategory("FileCascadeID", "MyCascade"),
				new MenuItemSeparator(),
				new MenuItemText("MyFileItem1", "description1"),
				new MenuItemText("MyFileItem2", "description2"),
			};
		}
		else 
        if(category.equals("ContextCategoryID"))
		{
            return new IMenuItem[]
			{
				new MenuItemText("CascadeItemID", "MyCascadeItem", "MyItem Description")
			};
		}
		else 
        if(category.equals("FileCascadeID"))
		{
            return new IMenuItem[]
			{
				new MenuItemText("FileItemID", "MyCascadeItem", "MyItem Description")
			};
		}
		return null;
	}

    /**
      * Notification that a menu item has been selected.
      */
	public void actionMenuSelected(IPage viewInstance, IMenuItem item)
	{
		System.out.println(item.getID());
		if(item.getID().equals("SelectHostsID"))
		{
			fireSelectTreeNode(viewInstance, _hosts);
		}
		else
		if(item.getID().equals("ExpandHostsID"))
		{
			fireExpandTreeNode(viewInstance, _hosts);
		}
	}

    /**
      * Notification that objects were selected in the resource tree.
      */
	public void actionObjectSelected(IPage viewInstance, IResourceObject[] selection, IResourceObject[] previousSelection)
	{
		super.actionObjectSelected(viewInstance, selection, previousSelection);
		if(selection[0] != null)
			System.out.println("selection[0] = " + selection);

		if((previousSelection != null) && (previousSelection[0] != null))
			System.out.println("previousSelection[0] = " + previousSelection);

		viewInstance.getFramework().getJFrame().setCursor(new FeedbackIndicator(_feedbackState? FeedbackIndicator.FEEDBACK_DEFAULT : FeedbackIndicator.FEEDBACK_WAIT));
		_feedbackState = !_feedbackState;
		System.out.println(_feedbackState);
		//fireChangeFeedbackCursor(viewInstance, (_feedbackState? FeedbackIndicator.FEEDBACK_DEFAULT : FeedbackIndicator.FEEDBACK_WAIT));
	}

    /**
      * Notification that the framework window is closing.
      * Called by ResourcePage
      */
	public void actionViewClosing(IPage viewInstance) throws CloseVetoException
	{
		//throw new CloseVetoException();
	}
}



class DynamicObject extends ResourceObject 
{
	DynamicObject(String name)
	{
		super(name);
	}
}


class DemoResourceObject extends ResourceObject
{
	public DemoResourceObject(String sDisplayName, Icon icon)
	{
		super(sDisplayName, icon, icon);
	}


	public DemoResourceObject(String name)
	{
		super(name);
	}

	/**
	 *	Called when this object is unselected, if it was previously
	 *  selected.  The parent parameter allows this object to interact
	 *  with (set context menus, status bar, etc.) the page view in which
	 *  it was selected.
	 */
	public void unselect(IPage viewInstance)
	{
		System.out.println(getName() + "::unselect()");
	}

	/**
	 *	Called when this object is selected.  The parent parameter allows
	 *  this object to interact with (set context menus, status bar, etc.)
	 *  the page view in which it was selected.  If you support multiple
	 *  selection, call parent.getSelectedObjects() to find out which other
	 *  objects are selected, and set the menus accordingly.
	 */
	public void select(IPage viewInstance)
	{
		System.out.println(getName() + "::select()");
	}

	/**
	 *	Called when user wants to execute this object, invoked by a
	 *  double-click or a menu action.  In the case of multiple 
	 *  selected objects, this method is called once for each object 
	 *  (in top-bottom order.)  If you support multiple selection, call
	 *  parent.getSelectedObjects() to find out which other objects 
	 *  are selected.  A return value of true indicates that a run action
	 *  has been initiated, and not to call run() on other selected objects.
	 *  TODO: return boolean
	 */
	public boolean run(IPage viewInstance)
	{
		System.out.println(getName() + "::run()");
		return true;
	}
}
