%define major_version 1.1
%define minor_version 0

Name: fedora-idm-console
Version: %{major_version}.%{minor_version}
Release: 3
Group: Applications
Vendor: Fedora Project
URL: http://directory.fedora.redhat.com
License: LGPL
Packager: Red Hat, Inc. <http://bugzilla.redhat.com/bugzilla>
Summary: Fedora Management Console
BuildRoot: %{_tmppath}/%{name}-%{version}-root
BuildArch: noarch
Source: %{name}-%{version}.tar.gz
Autoreq: 0
AutoReqProv: no
Requires: %{name}-framework >= %{version}
BuildPreReq: ant >= 1.6.2, ldapjdk >= 4.18, jss >=  4.2 

%description
A Java based remote management console used for Managing Fedora
Administration Server and Fedora Directory Server.

# prep and setup expect there to be a Source file
# in the /usr/src/redhat/SOURCES directory - it will be unpacked
# in the _builddir (not BuildRoot)

%package framework
Summary: Fedora Management Console Framework
Requires: ldapjdk >= 4.18, jss >= 4.2
Group: System Environment/Libraries

%description framework
A Java Management Console framework used for remote server management.

%prep
%setup -q
                                                                                
%build
cd console
ant -Dlib.dir=%{_libdir}
                                                                                
%install
rm -rf $RPM_BUILD_ROOT
install -d $RPM_BUILD_ROOT/usr/share/java
install -m777 built/release/jars/fedora-* $RPM_BUILD_ROOT/usr/share/java
install -d $RPM_BUILD_ROOT/usr/bin
install -m777 built/release/%{name} $RPM_BUILD_ROOT/usr/bin

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root)
/usr/share/java/%{name}-%{version}_en.jar
/usr/bin/%{name}

%files framework
%defattr(-,root,root)
/usr/share/java/%{name}-base-%{version}.jar
/usr/share/java/fedora-mcc-%{version}.jar
/usr/share/java/fedora-mcc-%{version}_en.jar
/usr/share/java/fedora-nmclf-%{version}.jar
/usr/share/java/fedora-nmclf-%{version}_en.jar

%post
cd /usr/share/java
ln -s %{name}-%{version}_en.jar %{name}-%{major_version}_en.jar
ln -s %{name}-%{version}_en.jar %{name}_en.jar

%post framework
cd /usr/share/java
ln -s %{name}-base-%{version}.jar %{name}-base-%{major_version}.jar
ln -s %{name}-base-%{version}.jar %{name}-base.jar
ln -s fedora-mcc-%{version}.jar fedora-mcc-%{major_version}.jar
ln -s fedora-mcc-%{version}.jar fedora-mcc.jar
ln -s fedora-mcc-%{version}_en.jar fedora-mcc-%{major_version}_en.jar
ln -s fedora-mcc-%{version}_en.jar fedora-mcc_en.jar
ln -s fedora-nmclf-%{version}.jar fedora-nmclf-%{major_version}.jar
ln -s fedora-nmclf-%{version}.jar fedora-nmclf.jar
ln -s fedora-nmclf-%{version}_en.jar fedora-nmclf-%{major_version}_en.jar
ln -s fedora-nmclf-%{version}_en.jar fedora-nmclf_en.jar

%preun
rm -rf /usr/share/java/%{name}-%{major_version}_en.jar
rm -rf /usr/share/java/%{name}_en.jar

%preun framework
rm -rf /usr/share/java/%{name}-base-%{major_version}.jar
rm -rf /usr/share/java/%{name}-base.jar
rm -rf /usr/share/java/fedora-mcc-%{major_version}.jar
rm -rf /usr/share/java/fedora-mcc.jar
rm -rf /usr/share/java/fedora-mcc-%{major_version}_en.jar
rm -rf /usr/share/java/fedora-mcc_en.jar
rm -rf /usr/share/java/fedora-nmclf-%{major_version}.jar
rm -rf /usr/share/java/fedora-nmclf.jar
rm -rf /usr/share/java/fedora-nmclf-%{major_version}_en.jar
rm -rf /usr/share/java/fedora-nmclf_en.jar

%changelog
* Fri Jul 27 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-3
- Changed package name to be less generic.

* Thu Jul 26 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-2
- Updated start script and theme jar names. Fixed post and
  preun steps for framework subpackage.

* Fri Jun 29 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-1
- Updated for 1.1.0 release

* Mon Nov 14 2005 Nathan Kinder <nkinder@redhat.com> 1.0-1
- Initial creation
