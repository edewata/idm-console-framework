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
package supermail;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import com.sun.java.swing.*;
import com.netscape.management.client.*;
import com.netscape.management.client.console.*;
import com.netscape.management.client.topology.*;
import com.netscape.management.client.util.*;
import com.netscape.management.nmclf.*;
import netscape.ldap.*;

/**
 * Netscape admin server 4.0 configuration entry point. The directory
 * server needs to contain the name of this class in order for the topology
 * view to load this class.
 */
public class SuperMailServer extends AbstractServerObject implements SuiConstants
{
	public static ResourceSet _resource = new ResourceSet("com.netscape.management.admserv.admserv");

	ConsoleInfo _consoleInfo;
	int _serverStatus;

	/**
	* Implements the IServerObject interface. Initializes the page with
	* the global information.
	*
	* @param info - global information.
	*/
	public void initialize(ConsoleInfo info) 
	{
		super.initialize(info);
		_consoleInfo = info;
		setIcon(new RemoteImage(_resource.getString("admin", "smallIcon")));
	}


    
    private boolean run(IPage viewInstance) {
        // Augment the console info with the server name
        _consoleInfo.put("SIE", getSIE());
        _consoleInfo.put("SERVER_NAME", getName());
        _consoleInfo.put("HOST_NAME", getHostName());

        createFramework();
        
        return true;
    }

    /**
     * This function is called when the admin server is double clicked on the topology view.
     */
    public boolean run(IPage viewInstance,
                       IResourceObject selectionList[]) {
        // create new frame
        if (selectionList.length == 1) {
            return run(viewInstance);
        }
	return false;
    }


    protected void createFramework() {
        Framework frame = new Framework(new SuperMailFrameworkInitializer(_consoleInfo));
    }    


    // return the host in which this server is installed
    protected String getHostName()
	{
		return (String)_nodeDataTable.get("serverHostName");
	}

    /**
     * Server name has format: "Netscape Administration Server (admin-serv-<host>:<port>)"
     * We want just admin-serv-<host> part as a SIE
     **/
    protected String getSIE() 
	{
		return (String)_nodeDataTable.get("cn");
    }
    
    public ConsoleInfo getConsoleInfo() {
        return _consoleInfo;
    }


    private URL _serverURL = null;
    private URLConnection _serverConnection = null;

    /**
     * Implements the IServerObject interface.
     */
    public synchronized int getServerStatus() {
	return STATUS_UNKNOWN;

    }    

    /**
     */
    public void cloneFrom(String referenceDN) {
    }


	static public void main(String argv[])
	{
		try
		{
			UIManager.setLookAndFeel( "com.netscape.management.nmclf.SuiLookAndFeel" );
		}	
		catch (Exception e)
		{
			System.out.println("Cannot load nmc look and feel.");
		}
		Framework.loadFontPreferences();
		
		ConsoleInfo _consoleInfo = new ConsoleInfo();
        //_consoleInfo.put("SIE", getSIE());
        //_consoleInfo.put("SERVER_NAME", getName());
        //_consoleInfo.put("HOST_NAME", getHostName());
        Framework frame = new Framework(new SuperMailFrameworkInitializer(_consoleInfo));
	}
}
