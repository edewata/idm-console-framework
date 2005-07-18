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
import com.netscape.management.client.console.*;

class ReplacementTaskModel extends TaskModel
{
    TaskObject root;

    public ReplacementTaskModel() {
        root = new TaskObject("Top 2 Tasks");

        TaskObject outline1 = new MyTaskObject("Manage users and groups",
                new ImageIcon("icons/task1.jpg"));
        TaskObject outline2 = new MyTaskObject("Install or upgrade server",
                new ImageIcon("icons/task2.jpg"));
        root.add(outline1);
        root.add(outline2);
		setRoot(root);
	}
}
	

public class DemoTaskModel extends TaskModel implements IMenuInfo {
    TaskObject _root[] = new TaskObject[2];

    public DemoTaskModel() {
        _root[0] = new TaskObject("Top 7 Tasks");

        TaskObject outline1 = new MyTaskObject("Manage users and groups",
                new ImageIcon("icons/task1.jpg"));
        TaskObject outline2 = new MyTaskObject("Install or upgrade server",
                new ImageIcon("icons/task2.jpg"));
        TaskObject outline3 =
                new MyTaskObject("Configure server performance",
                new ImageIcon("icons/task3.jpg"));
        TaskObject outline4 =
                new MyTaskObject("Setup security", new ImageIcon("icons/task4.jpg"));
        TaskObject outline5 = new MyTaskObject("Manage secure access",
                new ImageIcon("icons/task5.jpg"));
        TaskObject outline6 =
                new MyTaskObject("Setup programs and data access",
                new ImageIcon("icons/task6.jpg"));
        TaskObject outline7 =
                new MyTaskObject("Configure content and services",
                new ImageIcon("icons/task7.jpg"));
        TaskObject outline8 = new MyTaskObject("Monitor server performance",
                new ImageIcon("icons/task1.jpg"));
        TaskObject outline9 =
                new MyTaskObject("Backup and recover configuration",
                new ImageIcon("icons/task2.jpg"));
        TaskObject outlineA = new MyTaskObject("Manage server content",
                new ImageIcon("icons/task3.jpg"));

        outline1.setDescription("Specifies the cell at the upper left of the component's display area, where the upper-left-most cell has address.");
        outline2.setDescription("Properties behave in some ways like a memory location.");
        outline3.setDescription("Used when the component is smaller than its display area to determine where .");
        outline4.setDescription("Scriptlets let Web authors create reusable objects with Dynamic HTML.");
        outline5.setDescription("Components are a key technology for improving the amount and quality of software.");
        outline6.setDescription("You customize a GridBagConstraints object by setting one or more of its instance variables.");
        outline7.setDescription("Events are notices that a component transmits at run time to the outside application.");
        outline8.setDescription("Scriptlets are as secure as Dynamic HTML and script itself.");
        outline9.setDescription("This is a description for this task.");
        outlineA.setDescription("Properties act like named memory values inside the component.");

        _root[0].add(outline1);
        outline1.add(new TaskObject("Search for user or group"));
        outline1.add(new TaskObject("Add a new user"));
        outline1.add(new TaskObject("Add a new group"));
        _root[0].add(outline2);
        outline2.add(new TaskObject("Install/clone a new Enterprise 4.0 server"));
        outline2.add(new TaskObject("Upgrade server"));
        outline2.add(new TaskObject("Upgrade server with a field-level patch"));
        outline2.add(new TaskObject("Remove a server"));
        _root[0].add(outline3);
        _root[0].add(outline4);
        _root[0].add(outline5);
        _root[0].add(outline6);
        _root[0].add(outline7);

        _root[1] = new TaskObject("Monthly Tasks (Outline)");
        _root[1].add(outline8);
        _root[1].add(outline9);
        _root[1].add(outlineA);

    }

    /**
       * Notification that objects have been selected in the task list.
     * Called by TaskPage
       */
    public void actionObjectSelected(IPage viewInstance,
            ITaskObject selection, ITaskObject previousSelection) {
        System.out.println("DemoTaskModel.actionObjectSelected()");
        super.actionObjectSelected(viewInstance, selection,
                previousSelection);
        fireDisableMenuItem(viewInstance, "COMMON_ID");
    }

    public Object getRoot() {
        return _root[0];
    }

    public Object getRoot(int viewIndex) {
        return _root[viewIndex];
    }

    public Object getChild(Object parent, int index) {
        return ((TaskObject) parent).getChildAt(index);
    }

    public int getChildCount(Object parent) {
        return ((TaskObject) parent).getChildCount();
    }

    public boolean isLeaf(Object node) {
        return ((TaskObject) node).getChildCount() == 0;
    }

    public int getViewCount() {
        return _root.length;
    }

    public String getViewName(int index) {
        return _root[index].getName();
    }

    public int getViewType(int index) {
        if (index == 1)
            return ITaskModel.VIEW_BRIEF;
        else
            return ITaskModel.VIEW_DETAIL;
    }

    /**
       * Notification that the framework window is closing.
       * Called by ResourcePage
       */
    public void actionViewClosing(IPage viewInstance)
            throws CloseVetoException {
    }

    /**
      * Returns menu categories for this model.
      */
    public String[] getMenuCategoryIDs() {
        return new String[]{ Framework.MENU_FILE,
        TaskPage.MENU_CONTEXT, "ContextCategoryID", "FileCascadeID" };
    }

    /**
      * Returns menu items for this model.
      */
    public IMenuItem[] getMenuItems(String category) {
        String COMMON_ID = "COMMON_ID";
        if (category.equals(ResourcePage.MENU_CONTEXT)) {
            return new IMenuItem[]{ new MenuItemCategory("ContextCategoryID",
                    "MyCategory1"), new MenuItemSeparator(),
            new MenuItemText("MyContextItem1_ID", "MyContextItem1",
                    "description1"),
            new MenuItemText("MyContextItem2_ID", "MyContextItem2",
                    "description2"),
            new MenuItemText(COMMON_ID, "CommonMenuItem", "description"), };
        } else if (category.equals(Framework.MENU_FILE)) {
            return new IMenuItem[]{ new MenuItemCategory("FileCascadeID",
                    "MyCascade"), new MenuItemSeparator(),
            new MenuItemText("MyFileItem1_ID", "MyFileItem1",
                    "description1"),
            new MenuItemText("MyFileItem2_ID", "MyFileItem2",
                    "description2"),
            new MenuItemText("ReplaceTaskModel_ID", "Replace Task Model",
                    "description2"),
            new MenuItemText(COMMON_ID, "CommonMenuItem", "description"), };
        } else if (category.equals("ContextCategoryID")) {
            return new IMenuItem[]{ new MenuItemText("CascadeItemID", "MyCascadeItem",
                    "MyItem Description")};
        } else if (category.equals("FileCascadeID")) {
            return new IMenuItem[]{ new MenuItemText("FileItemID", "MyCascadeItem",
                    "MyItem Description")};
        }
        return null;
    }


    /**
       * Notification that a menu item has been selected.
       */
    public void actionMenuSelected(IPage viewInstance, IMenuItem item) {
        System.out.println(item.getID());
		if(item.getID().equals("ReplaceTaskModel_ID"))
		{
			((TaskPage)viewInstance).setTaskModel(new ReplacementTaskModel());
		}
    }

}


class MyTaskObject extends TaskObject {
    static int progress = -1;

    public MyTaskObject(String name) {
        super(name);
    }

    public MyTaskObject(String name, Icon icon) {
        super(name, icon);
    }

    /**
      * Called when this object is selected.
         * Called by: TaskModel
      */
    public void select(IPage viewInstance) {
        System.out.println("MyTaskObject.select() disabling MyFileItem1_ID");
        fireDisableMenuItem(viewInstance, "MyFileItem1_ID");
        fireChangeStatusItemState(viewInstance,
                TaskPage.STATUS_PROGRESS, new Integer(progress));
        progress += 20;
        if (progress > 100)
            progress = -1;
    }

    public boolean run(IPage viewInstance) {
        System.out.println("MyTaskObject.run() viewInstance = " +
                viewInstance);
        return true;
    }
}
