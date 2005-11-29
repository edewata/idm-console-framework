Name: fedora-console
Version: 1.0
Release: 1
Group: Applications
Vendor: Fedora Project
URL: http://directory.fedora.redhat.com
License: LGPL
Packager: Red Hat, Inc. <http://bugzilla.redhat.com/bugzilla>
Summary: Fedora Management Console
BuildRoot: %{_tmppath}/%{name}-%{version}-root
Source: %{name}-%{version}.tar.gz
Autoreq: 0
AutoReqProv: no
Requires: ldapjdk >= 4.17, jss >= 3.6
BuildPreReq: ldapjdk >= 4.17, jss >= 3.6  

%description
A Java based remote management console used for Managing Fedora
Administration Server and Fedora Directory Server.

# prep and setup expect there to be a Source file
# in the /usr/src/redhat/SOURCES directory - it will be unpacked
# in the _builddir (not BuildRoot)
%prep
%setup -q
                                                                                
%build
cd console
ant
                                                                                
%install
rm -rf $RPM_BUILD_ROOT
install -d $RPM_BUILD_ROOT/usr/share/java
install -m777 built/release/jars/fedora-* $RPM_BUILD_ROOT/usr/share/java
install -d $RPM_BUILD_ROOT/usr/bin
install -m777 console/startconsole $RPM_BUILD_ROOT/usr/bin

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root)
/usr/share/java/fedora-base-%{version}.jar
/usr/share/java/fedora-mcc-%{version}.jar
/usr/share/java/fedora-mcc-%{version}_en.jar
/usr/share/java/fedora-nmclf-%{version}.jar
/usr/share/java/fedora-nmclf-%{version}_en.jar
/usr/bin/startconsole
                                                                                
%post
cd /usr/share/java
ln -s fedora-base-%{version}.jar fedora-base.jar
ln -s fedora-mcc-%{version}.jar fedora-mcc.jar
ln -s fedora-mcc-%{version}_en.jar fedora-mcc_en.jar
ln -s fedora-nmclf-%{version}.jar fedora-nmclf.jar
ln -s fedora-nmclf-%{version}_en.jar fedora-nmclf_en.jar

%preun
rm -rf /usr/share/java/fedora-base.jar
rm -rf /usr/share/java/fedora-mcc.jar
rm -rf /usr/share/java/fedora-mcc_en.jar
rm -rf /usr/share/java/fedora-nmclf.jar
rm -rf /usr/share/java/fedora-nmclf_en.jar

%changelog
* Mon Nov 14 2005 Nathan Kinder <nkinder@redhat.com> 1.0-1
- Initial creation
