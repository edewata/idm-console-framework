@rem // BEGIN COPYRIGHT BLOCK
@rem // Copyright (C) 2005 Red Hat, Inc.
@rem // All rights reserved.
@rem // 
@rem // This library is free software; you can redistribute it and/or
@rem // modify it under the terms of the GNU Lesser General Public
@rem // License as published by the Free Software Foundation version
@rem // 2.1 of the License.
@rem //                                                                                 
@rem // This library is distributed in the hope that it will be useful,
@rem // but WITHOUT ANY WARRANTY; without even the implied warranty of
@rem // MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
@rem // Lesser General Public License for more details.
@rem //                                                                                 
@rem // You should have received a copy of the GNU Lesser General Public
@rem // License along with this library; if not, write to the Free Software
@rem // Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
@rem // END COPYRIGHT BLOCK
echo ###
echo ### Remember to setup NETSCAPE_SERVER_ROOT variable
echo ###
echo ### building
mkdir classes
mkdir classes\images
javac -d classes -classpath "classes;%NETSCAPE_SERVER_ROOT%\java\base.jar;%NETSCAPE_SERVER_ROOT%\java\ldapjdk.jar;%NETSCAPE_SERVER_ROOT%\java\mcc40.jar;%NETSCAPE_SERVER_ROOT%\java\mcc40_en.jar;%NETSCAPE_SERVER_ROOT%\java\nmclf40.jar;%NETSCAPE_SERVER_ROOT%\java\nmclf40_en.jar;%NETSCAPE_SERVER_ROOT%\java\swingall.jar;%CLASSPATH%" configuration\ConfigurationNode.java configuration\ConfigurationNodeRHP.java configuration\ConfigurationPage.java configuration\OperationNode.java configuration\OperationNodeRHP.java configuration\SuperMailServerNode.java configuration\SuperMailServerNodeRHP.java customview\SuperMailCustomView.java status\AccessLogModel.java status\ErrorLogModel.java status\LoggingNode.java status\PerformanceNode.java status\StatusPage.java topology\SuperMailTopologyPlugin.java ug\SuperMailUserPage.java
javac -d classes -classpath "classes;%NETSCAPE_SERVER_ROOT%\java\base.jar;%NETSCAPE_SERVER_ROOT%\java\ldapjdk.jar;%NETSCAPE_SERVER_ROOT%\java\mcc40.jar;%NETSCAPE_SERVER_ROOT%\java\mcc40_en.jar;%NETSCAPE_SERVER_ROOT%\java\nmclf40.jar;%NETSCAPE_SERVER_ROOT%\java\nmclf40_en.jar;%NETSCAPE_SERVER_ROOT%\java\swingall.jar;%CLASSPATH%" SuperMailFrameworkInitializer.java SuperMailServer.java 
copy *.properties classes
copy classes.env classes
copy images\* classes\images
cd classes
jar c0f ..\SuperMailPlugin.jar * 
cd ..
copy SuperMailPlugin.jar SuperMailPlugin_en.jar
