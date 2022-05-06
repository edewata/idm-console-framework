################################################################################
Name:             idm-console-framework
################################################################################

%global           product_id dogtag-console-framework

Summary:          Identity Management Console Framework
URL:              https://github.com/dogtagpki/idm-console-framework
License:          LGPLv2

BuildArch:        noarch

# For development (i.e. unsupported) releases, use x.y.z-0.n.<phase>.
# For official (i.e. supported) releases, use x.y.z-r where r >=1.
Version:          2.0.0
Release:          1%{?_timestamp}%{?_commit_id}%{?dist}
#global           _phase -alpha1

# To create a tarball from a version tag:
# $ git archive \
#     --format=tar.gz \
#     --prefix idm-console-framework-<version>/ \
#     -o idm-console-framework-<version>.tar.gz \
#     <version tag>
Source: https://github.com/dogtagpki/idm-console-framework/archive/v%{version}%{?_phase}/idm-console-framework-%{version}%{?_phase}.tar.gz

# To create a patch for all changes since a version tag:
# $ git format-patch \
#     --stdout \
#     <version tag> \
#     > idm-console-framework-VERSION-RELEASE.patch
# Patch: idm-console-framework-VERSION-RELEASE.patch

################################################################################
# Java
################################################################################

%define java_devel java-17-openjdk-devel
%define java_headless java-17-openjdk-headless
%define java_home %{_jvmdir}/jre-17-openjdk

################################################################################
# Build Dependencies
################################################################################

BuildRequires:    %{java_devel}
BuildRequires:    ant >= 1.6.2
BuildRequires:    jss >= 5.0
BuildRequires:    ldapjdk >= 5.0

%description
A Java Management Console framework used for remote server management.

################################################################################
%package -n %{product_id}
################################################################################

Summary:          Identity Management Console Framework

Requires:         %{java_headless}
Requires:         jss >= 5.0
Requires:         ldapjdk >= 5.0

%if "%{product_id}" != "idm-console-framework"
Obsoletes:        idm-console-framework < %{version}-%{release}
Provides:         idm-console-framework = %{version}-%{release}
%endif

%description -n %{product_id}
A Java Management Console framework used for remote server management.

################################################################################
%prep
################################################################################

%autosetup -n idm-console-framework-%{version}%{?_phase} -p 1

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
%files -n %{product_id}
################################################################################

%doc LICENSE
%{_javadir}/idm-console-base.jar
%{_javadir}/idm-console-mcc.jar
%{_javadir}/idm-console-mcc_en.jar
%{_javadir}/idm-console-nmclf.jar
%{_javadir}/idm-console-nmclf_en.jar

################################################################################
%changelog
* Thu Aug 09 2018 Dogtag PKI Team <pki-team@redhat.com> 1.2.0-0
- To list changes in <branch> since <tag>:
  $ git log --pretty=oneline --abbrev-commit --no-decorate <tag>..<branch>
