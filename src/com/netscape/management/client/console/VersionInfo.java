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
package com.netscape.management.client.console;

import java.util.*;
import java.io.*;
import com.netscape.management.client.util.*;

/**
 * Provides version information for Console.
 * The version data is stored in the "console.properties" file.
 */
public class VersionInfo {

    private static ResourceSet _resource = new ResourceSet("com.netscape.management.client.console.versioninfo");

    /**
     * Returns version number for the Netacape Console
     * VersionInfo class is introduces in Console 4.1. As 4.1 Netscape Servers
     * may be started under 4.0 console as well, the proper way to test for
     * version is:
     * <pre>
     *     String version, build;
     *     try {
     *         version = VersionInfo.getVersionNumber();
     *     }
     *     // If the class is not found then we are running in 4.0 environment
     *     catch (ClassNotFoundException e) {
     *         version = "4.0";
     *     }
     *     Debug.println("version="+version);
     *
     * </pre>
     *
     * @return  Netscape Console Version Number
     */
    public static String getVersionNumber() {
        return _resource.getString("console","versionNumber");
    }

    /**
      * Returns build number for the Netacape Console.
      *
      * @return  Netscape Console Build Number
      */
    public static String getBuildNumber() {
        return _resource.getString("console","buildNumber");
    }
}
