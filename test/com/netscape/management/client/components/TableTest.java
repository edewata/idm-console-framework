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
package com.netscape.management.client.components.test;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import javax.swing.event.*;
import com.netscape.management.nmclf.*;
import com.sun.java.swing.plaf.windows.*;

/**
* TODO: improve Javadocs
*/
public class TableTest
{
    public static void main(String argv[])
    {
        try 
        {
            UIManager.setLookAndFeel(new SuiLookAndFeel());
            //UIManager.setLookAndFeel(new WindowsLookAndFeel());
            FontFactory.initializeLFFonts(); // load default customized fonts for login/splash
        }
        catch (Exception e) 
        {
            System.err.println("cannot load lf: " + e);
        }
        
        JFrame frame = new JFrame();
		frame.addWindowListener
		(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e) 
				{
					System.exit(0);
				}
			}
		);

        Table table = new Table(new TextTableModel(), true);
        //JTable table = new JTable(new TextTableModel());
        //table.setAutoResizeMode(table.AUTO_RESIZE_OFF);
        //DetailTable table = new DetailTable(new NumericTableModel(), true);
        table.setPreferredScrollableViewportSize(new Dimension(600,100));
        frame.getContentPane().add(table.createScrollPaneForTable(table));
        //frame.getContentPane().add(table);
        //JDialog d = new TableCustomizationDialog(frame, table.getJTable());
        //d.show();
        //System.exit(0);
		frame.pack();
		frame.show();
    }
}


class NumericTableModel extends AbstractTableModel{
    public int getColumnCount() 
    { 
        return 5; 
    }
    
    public int getRowCount() 
    { 
        return 50;
    }
    
    public Object getValueAt(int row, int col) 
    { 
        if(col == 2 && row == 4)
            return new Integer(1234567); 
        return new Integer((1+row)*(1+col)); 
    }
    
    public String getColumnName(int col) 
    {
        String[] cols = { "Grand", "Splendiferous", "Magnificent", "Splendid", "Ostentatious" };
        return cols[col];
    }
    
    public Class getColumnClass(int c)     {        return getValueAt(0, c).getClass();
    }

}



class TextTableModel extends AbstractTableModel
{    final String[] columnNames = {"First Name", 
                                  "Last Name",
                                  "Sport",
                                  "# of Years",
                                  "Vegetarian"};
    final Object[][] data =     {
        {"Mary", "Campione", "Snowboarding", new Integer(5), new Boolean(false)},
        {"Alison", "Huml", "Rowing", new Integer(3), new Boolean(true)},
        {"Kathy", "Walrath", "Chasing toddlers", new Integer(2), new Boolean(false)},
        {"Mark", "Andrews", "Speed reading", new Integer(20), new Boolean(true)},
        {"Angela", "Lih", "Teaching high school", new Integer(4), new Boolean(false)}
    };

    public int getColumnCount()     {
        return columnNames.length;
    }
                
    public int getRowCount()     {
        return data.length;
    }

    public String getColumnName(int col)     {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col)     {
        return data[row][col];
    }

    public Class getColumnClass(int c)     {        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col)     {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 2) { 
            return false;
        } else {
            return true;
        }
    }}