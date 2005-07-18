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
echo ### Remember to setup NETSCAPE_SERVER_ROOT environment variable
echo ###
javac -classpath "%NETSCAPE_SERVER_ROOT%\java\base.jar;%NETSCAPE_SERVER_ROOT%\java\ldapjdk.jar;%NETSCAPE_SERVER_ROOT%\java\mcc40.jar;%NETSCAPE_SERVER_ROOT%\java\mcc40_en.jar;%NETSCAPE_SERVER_ROOT%\java\nmclf40.jar;%NETSCAPE_SERVER_ROOT%\java\nmclf40_en.jar;%NETSCAPE_SERVER_ROOT%\java\swingall.jar;%CLASSPATH%" *.java

