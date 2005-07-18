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
import com.sun.java.swing.*;
import com.sun.java.swing.event.*;
import com.netscape.management.client.*;

public class DemoFrameworkInitializer extends FrameworkInitializer {
    public DemoFrameworkInitializer() {
        try {
            UIManager.setLookAndFeel("com.netscape.management.nmclf.SuiLookAndFeel");
        } catch (Exception e) {
            System.out.println("Cannot load nmc look and feel.");
        }
        Framework.loadFontPreferences();
        addPage(new TaskPage(new DemoTaskModel()));
        ResourcePage rp = new DemoResourcePage(new DemoResourceModel());
        addPage(rp);
        setFrameTitle("Demo Framework");
    }

    static public void main(String args[]) {
        new Framework(new DemoFrameworkInitializer());
    }
}
class DemoResourcePage extends ResourcePage {
    IStatusItem statusItemSpacer = new StatusItemSpacer("SPACER", 2);
    IStatusItem statusItemIcon = new StatusItemIcon("ICON");

    public DemoResourcePage(IResourceModel resourceModel) {
        super(resourceModel);
    }

    protected void populateStatusItems() {
        System.out.println("populateStatusItems()");
        super.populateStatusItems();
        if (isPageSelected()) {
            _framework.addStatusItem(statusItemSpacer,
                    IStatusItem.LEFTFIRST);
            _framework.addStatusItem(statusItemIcon, IStatusItem.LEFTFIRST);
        }
    }

    protected void unpopulateStatusItems() {
        System.out.println("unpopulateStatusItems()");
        super.unpopulateStatusItems();
        if (isPageSelected()) {
            _framework.removeStatusItem(statusItemSpacer);
            _framework.removeStatusItem(statusItemIcon);
        }
    }

    public void valueChanged(TreeSelectionEvent ev) {
        super.valueChanged(ev);
        IResourceObject[] selection = getSelection();
        if (selection != null) {
            if (selection.length == 1)// single selection
            {
                _framework.changeStatusItemState("ICON",
                        selection[0].getIcon());
                _framework.changeStatusItemState(Framework.STATUS_TEXT,
                        selection[0].getName());
            } else // multiple selection  TODO: determine proper behavior
            {
            }
        }
    }
}
