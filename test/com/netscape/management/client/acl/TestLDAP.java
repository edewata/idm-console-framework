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
package com.netscape.management.client.acl;

import com.netscape.management.client.util.Debug;

public class TestLDAP {
    public static void main(String args[]) throws Exception {
        String user = "cn=Directory Manager";
        String pw = "wastewater";
        String dn = "o=Airius.com";
        /*String dn   = "cn=Stop, cn=Operation, cn=Tasks, cn=admin-serv-skydome, cn=Netscape Administration Server, cn=Suitespot 4.0, cn=skydome.mcom.com, ou=Netscape SuiteSpot, o=NetscapeRoot";*/

        LdapACL lacl = new LdapACL("skydome", 389, user, pw);

        Debug.setTrace(true);

        lacl.retrieveACL(dn);

        System.out.println(lacl);

        //lacl.getRule(0).setRight("all");

        //System.out.println(lacl);

        lacl.updateACL(dn);

        System.exit(0);
    }
}
