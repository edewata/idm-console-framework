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
import com.sun.java.swing.*;
import com.netscape.management.client.*;
import com.netscape.management.client.logging.*;
import com.netscape.management.nmclf.*;

class LoggingNode extends ResourceObject
{
	public LoggingNode()
	{
		super("Logs");
		setIcon(new ImageIcon("images/logging.gif"));
	}
	
	public Component getCustomPanel()
	{
		JTabbedPane tp = new JTabbedPane();
		JPanel accessPanel = new JPanel();
		accessPanel.setLayout(new BorderLayout());
		accessPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		accessPanel.add(new LogViewer(new AccessLogModel()));
		tp.addTab("Access", accessPanel);
		
		JPanel errorPanel = new JPanel();
		errorPanel.setLayout(new BorderLayout());
		errorPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
		errorPanel.add(new LogViewer(new AccessLogModel()));
		tp.addTab("Errors", errorPanel);
		return tp;
	}
}
