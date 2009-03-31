%define major_version 1.1
%define minor_version 3

Name: idm-console-framework
Version: %{major_version}.%{minor_version}
Release: 1%{?dist}
Summary: Identity Management Console Framework

Group: System Environment/Libraries
License: LGPLv2
URL: http://directory.fedoraproject.org

BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)
BuildArch: noarch
Source: http://directory.fedoraproject.org/sources/%{name}-%{version}.tar.bz2
Requires: ldapjdk
Requires: jss >= 4.2
# Urge use of OpenJDK for runtime
Requires: java >= 1:1.6.0
BuildRequires: java-devel >= 1:1.6.0
BuildRequires: ant >= 1.6.2
BuildRequires: ldapjdk
BuildRequires: jss >=  4.2 

%description
A Java Management Console framework used for remote server management.

%prep
%setup -q

%build
%{ant} \
    -Dlib.dir=%{_libdir} \
    -Dbuilt.dir=`pwd`/built \
    -Dclassdest=%{_javadir}

%install
rm -rf $RPM_BUILD_ROOT
install -d $RPM_BUILD_ROOT%{_javadir}
install -m644 built/release/jars/idm-console-* $RPM_BUILD_ROOT%{_javadir}

# create symlinks
pushd $RPM_BUILD_ROOT%{_javadir}
ln -s idm-console-base-%{version}.jar idm-console-base-%{major_version}.jar
ln -s idm-console-base-%{version}.jar idm-console-base.jar
ln -s idm-console-mcc-%{version}.jar idm-console-mcc-%{major_version}.jar
ln -s idm-console-mcc-%{version}.jar idm-console-mcc.jar
ln -s idm-console-mcc-%{version}_en.jar idm-console-mcc-%{major_version}_en.jar
ln -s idm-console-mcc-%{version}_en.jar idm-console-mcc_en.jar
ln -s idm-console-nmclf-%{version}.jar idm-console-nmclf-%{major_version}.jar
ln -s idm-console-nmclf-%{version}.jar idm-console-nmclf.jar
ln -s idm-console-nmclf-%{version}_en.jar idm-console-nmclf-%{major_version}_en.jar
ln -s idm-console-nmclf-%{version}_en.jar idm-console-nmclf_en.jar
popd

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root,-)
%doc LICENSE
%{_javadir}/idm-console-base-%{version}.jar
%{_javadir}/idm-console-base-%{major_version}.jar
%{_javadir}/idm-console-base.jar
%{_javadir}/idm-console-mcc-%{version}.jar
%{_javadir}/idm-console-mcc-%{major_version}.jar
%{_javadir}/idm-console-mcc.jar
%{_javadir}/idm-console-mcc-%{version}_en.jar
%{_javadir}/idm-console-mcc-%{major_version}_en.jar
%{_javadir}/idm-console-mcc_en.jar
%{_javadir}/idm-console-nmclf-%{version}.jar
%{_javadir}/idm-console-nmclf-%{major_version}.jar
%{_javadir}/idm-console-nmclf.jar
%{_javadir}/idm-console-nmclf-%{version}_en.jar
%{_javadir}/idm-console-nmclf-%{major_version}_en.jar
%{_javadir}/idm-console-nmclf_en.jar

%changelog
* Tue Mar 31 2009 Rich Megginson <rmeggins@redhat.com> 1.1.3-1
- this is the 1.1.3 release
- use the epoch with the java-devel version

* Wed Jul  2 2008 Rich Megginson <rmeggins@redhat.com> 1.1.2-1
- numerous fixes for threading issues and help for debugging and eclipse

* Tue Apr 15 2008 Rich Megginson <rmeggins@redhat.com> 1.1.1-3
- use java > 1.5.0 for the requirements

* Mon Apr 14 2008 Rich Megginson <rmeggins@redhat.com> 1.1.1-2
- install jar files with mode 644

* Wed Jan  9 2008 Rich Megginson <rmeggins@redhat.com> 1.1.1-1
- fix rpmlint issues
- changed license from LGPL to LGPLv2
- added explicit requires for java-1.7.0-icedtea
- added LICENSE for doc

* Wed Dec 19 2007 Rich Megginson <rmeggins@redhat.com> 1.1.0-2
- for the fedora ds 1.1 release

* Wed Aug  1 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-1
- Initial creation (based on old fedora-idm-console package).
