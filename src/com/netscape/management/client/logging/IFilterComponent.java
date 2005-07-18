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
package com.netscape.management.client.logging;

import java.awt.*;
import javax.swing.table.*;

/**
  * Allows the Log Viewer to communicate with a filter component.
  *
  * @date 11/29/97
  * @author ahakim@netscape.com
  */
public interface IFilterComponent {
    /**
       * Is called by the
     * is a Component, it is set in the detail pane, otherwise the
     * toString() value of object is displayed as text.
     * Called by LogViewer
       */
    public abstract Object getFilter();
}
