%define major_version 1.1
%define minor_version 0

Name: fedora-idm-console
Version: %{major_version}.%{minor_version}
Release: 4
Summary: Fedora Management Console

Group: Applications
License: LGPL
URL: http://directory.fedora.redhat.com

BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)
BuildArch: noarch
Source: %{name}-%{version}.tar.bz2
Requires: %{name}-framework >= %{version}
BuildRequires: ant >= 1.6.2
BuildRequires: ldapjdk
BuildRequires: jss >=  4.2 

%description
A Java based remote management console used for Managing Fedora
Administration Server and Fedora Directory Server.

%package framework
Summary: Fedora Management Console Framework
BuildRequires: ldapjdk
BuildRequires: jss >= 4.2
Group: System Environment/Libraries

%description framework
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
install -m777 built/release/jars/fedora-* $RPM_BUILD_ROOT%{_javadir}
install -d $RPM_BUILD_ROOT%{_bindir}
install -m777 built/release/%{name} $RPM_BUILD_ROOT%{_bindir}

# create symlinks
pushd $RPM_BUILD_ROOT%{_javadir}
ln -s %{name}-%{version}_en.jar %{name}-%{major_version}_en.jar
ln -s %{name}-%{version}_en.jar %{name}_en.jar
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
popd

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(-,root,root)
%{_javadir}/%{name}-%{version}_en.jar
%{_javadir}/%{name}-%{major_version}_en.jar
%{_javadir}/%{name}_en.jar
%{_bindir}/%{name}

%files framework
%defattr(-,root,root)
%{_javadir}/%{name}-base-%{version}.jar
%{_javadir}/%{name}-base-%{major_version}.jar
%{_javadir}/%{name}-base.jar
%{_javadir}/fedora-mcc-%{version}.jar
%{_javadir}/fedora-mcc-%{major_version}.jar
%{_javadir}/fedora-mcc.jar
%{_javadir}/fedora-mcc-%{version}_en.jar
%{_javadir}/fedora-mcc-%{major_version}_en.jar
%{_javadir}/fedora-mcc_en.jar
%{_javadir}/fedora-nmclf-%{version}.jar
%{_javadir}/fedora-nmclf-%{major_version}.jar
%{_javadir}/fedora-nmclf.jar
%{_javadir}/fedora-nmclf-%{version}_en.jar
%{_javadir}/fedora-nmclf-%{major_version}_en.jar
%{_javadir}/fedora-nmclf_en.jar

%changelog
* Fri Jul 27 2007 Rich Megginson <rmeggins@redhat.com> 1.1.0-4
- fedora-ized build - clean up in prep for package review

* Fri Jul 27 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-3
- Changed package name to be less generic.

* Thu Jul 26 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-2
- Updated start script and theme jar names. Fixed post and
  preun steps for framework subpackage.

* Fri Jun 29 2007 Nathan Kinder <nkinder@redhat.com> 1.1.0-1
- Updated for 1.1.0 release

* Mon Nov 14 2005 Nathan Kinder <nkinder@redhat.com> 1.0-1
- Initial creation
