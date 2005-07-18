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

import com.netscape.management.client.acleditor.ACLEditor;

public class ACLExample
{
   public static void main(String args[]) throws Exception
   {
      String user  = "cn=Directory Manager";
      String pw    = "wastewater";
      String aclDN = "o=Airius.com";
      String ugDN  = "o=Airius.com";

      try { com.sun.java.swing.UIManager.setLookAndFeel("com.netscape.management.nmclf.SuiLookAndFeel"); }
      catch (Exception e) { System.err.println("Unable to set l&f"); }

      ACLEditor ed = new ACLEditor("skydome", 389, user, pw, ugDN, aclDN);

      while (true)
      {
         try { Thread.currentThread().sleep(100); }
	 catch (Exception e ) { }
      }
   }
}
