################################################################################
Name:             idm-console-framework
################################################################################

%define major_version 1.1
%define minor_version 17

Version: %{major_version}.%{minor_version}
Release: 4%{?_timestamp}%{?_commit_id}%{?dist}
Summary: Identity Management Console Framework

License: LGPLv2
URL: https://directory.fedoraproject.org

BuildArch: noarch

Source: https://github.com/dogtagpki/idm-console-framework/archive/v%{version}%{?_phase}/idm-console-framework-%{version}%{?_phase}.tar.gz

################################################################################
# Runtime Dependencies
################################################################################

Requires: ldapjdk
Requires: jss >= 4.2.6-35
# Urge use of OpenJDK for runtime
Requires: java >= 1.8.0

################################################################################
# Build Dependencies
################################################################################

BuildRequires: java-devel >= 1.8.0
BuildRequires: ant >= 1.6.2
BuildRequires: ldapjdk
BuildRequires: jss >=  4.2.6-35

%description
A Java Management Console framework used for remote server management.

################################################################################
%prep
################################################################################

%setup -q

################################################################################
%build
################################################################################

%{ant} \
    -Dlib.dir=%{_libdir} \
    -Dbuilt.dir=`pwd`/built \
    -Dclassdest=%{_javadir}

################################################################################
%install
################################################################################

install -d $RPM_BUILD_ROOT%{_javadir}
install -m644 built/release/jars/idm-console-* $RPM_BUILD_ROOT%{_javadir}

################################################################################
%files
################################################################################

%doc LICENSE
%{_javadir}/idm-console-base.jar
%{_javadir}/idm-console-mcc.jar
%{_javadir}/idm-console-mcc_en.jar
%{_javadir}/idm-console-nmclf.jar
%{_javadir}/idm-console-nmclf_en.jar

################################################################################
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
