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
import com.netscape.management.client.logging.*;
import com.netscape.management.nmclf.*;
import netscape.ldap.*;

public class ErrorLogModel extends LogViewerModel
{
	private boolean _isInitialized = false;

	private String date[] = 
	{ 
		"Mon, 11 Aug 1998",
		"Tue, 12 Aug 1998",
		"Wed, 13 Aug 1998",
		"Thu, 14 Aug 1998",
		"Fri, 15 Aug 1998",
		"Sat, 16 Aug 1998",
		"Sun, 17 Aug 1998",
	};
	
	private String time[] = 
	{ 
		"00:19:19 -0700 (PDT)",
		"10:14:24 -0700 (PDT)",
		"06:22:56 -0700 (PDT)",
		"20:15:25 -0700 (PDT)",
		"03:23:31 -0700 (PDT)",
	};
	
	private String level[] =
	{
		"Warning",
		"Severe",
		"Information",
		"Information",
		"Information",
		"Information",
	};
	
	private String detail[] =
	{
		"Out of disk space",
		"Hack attempt",
		"Indexes corrupt",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unable to deliver message",
		"Unknown Mailbox",
		"Unknown Mailbox",
		"Unknown Mailbox",
		"Unknown Mailbox",
		"Unknown Mailbox",
		"Unknown Mailbox",
		"Unknown Mailbox",
	};
	
	public ErrorLogModel()
	{
		super();
		addColumn("#");
		addColumn("Date");
		addColumn("Time");
		addColumn("Level");
		addColumn("Detail");
		_isInitialized = true;
	}

	public void populateRows(int rowOffset, int numRows)
	{
		Object row[] = new Object[5];
		for(int rowIndex = 0; rowIndex < numRows; rowIndex++)
		{
			row[0] = Integer.toString(rowOffset + rowIndex);
			row[1] = date[rowIndex % date.length];
			row[2] = time[rowIndex % time.length];
			row[3] = level[rowIndex % level.length];
			row[4] = detail[rowIndex % detail.length];
			setValueAt(row[0], rowIndex, 0);
			setValueAt(row[1], rowIndex, 1);
			setValueAt(row[2], rowIndex, 2);
			setValueAt(row[3], rowIndex, 3);
			setValueAt(row[4], rowIndex, 4);
		}
	}

	public int getLogLength()
	{
		if(!_isInitialized)
			return -1;

		return 500;
	}
}
