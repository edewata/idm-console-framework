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

package com.netscape.management.client.security.test;

import javax.swing.*;
import java.util.*;
import java.io.*;

import java.security.cert.*;

import com.netscape.management.client.security.PromptForTrustDialog;
import com.netscape.management.nmclf.*;

import org.mozilla.jss.ssl.SSLCertificateApprovalCallback.ValidityStatus;

public class viewCert {
    public static void main(String arg[]) {
        JFrame f = new JFrame();

        try {
            UIManager.setLookAndFeel(new SuiLookAndFeel());
        } catch (Exception e) {}


//            X509Certificate cert = CertficateFactory.getInstance().generateCertificate(new X509Certificate();
//            cert.certificateList = new Vector();
//            try {
//                cert.certificateList.addElement(new X509(new File("server-cert.der")));
//                cert.certificateList.addElement(new X509(new File("ca-cert.der")));
//            } catch (Exception e) {} 

        X509Certificate cert = null;
        try {
          FileInputStream fis = new FileInputStream("server-cert.der");
          CertificateFactory cf = CertificateFactory.getInstance("X.509");
          Collection c = cf.generateCertificates(fis);
          Iterator i = c.iterator();

          while (i.hasNext()) {
              cert = (X509Certificate)i.next();
              System.out.println(cert);
          }
        } catch (Exception e) {}
          
          PromptForTrustDialog trust = new PromptForTrustDialog(f, cert,
                                                                new ValidityStatus());
//                                                                  ISSLServerAuthError.CERT_EXPIRED |
//                                                                  ISSLServerAuthError.INVALID_SIGNATURE,
//                                                                  ISSLServerAuthError.INVALID_COMMON_NAME |
//                                                                  ISSLServerAuthError.CERT_EXPIRED |
//                                                                  ISSLServerAuthError.INVALID_SIGNATURE);
          
          trust.show();
          System.exit(0);
          }
}
