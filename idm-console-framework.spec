################################################################################
Name:             idm-console-framework
################################################################################

Summary:          Identity Management Console Framework
URL:              http://www.dogtagpki.org/
License:          LGPLv2

BuildArch:        noarch

# For development (i.e. unsupported) releases, use x.y.z-0.n.<phase>.
# For official (i.e. supported) releases, use x.y.z-r where r >=1.
Version:          1.3.0
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

%if 0%{?fedora} && 0%{?fedora} <= 32 || 0%{?rhel} && 0%{?rhel} <= 8
%define java_devel java-1.8.0-openjdk-devel
%define java_headless java-1.8.0-openjdk-headless
%define java_home /usr/lib/jvm/java-1.8.0-openjdk
%else
%define java_devel java-11-openjdk-devel
%define java_headless java-11-openjdk-headless
%define java_home /usr/lib/jvm/java-11-openjdk
%endif

################################################################################
# Build Dependencies
################################################################################

BuildRequires:    %{java_devel}
BuildRequires:    ant >= 1.6.2
BuildRequires:    jss = 4.9
BuildRequires:    ldapjdk = 4.23

################################################################################
# Runtime Dependencies
################################################################################

Requires:         %{java_headless}
Requires:         jss = 4.9
Requires:         ldapjdk = 4.23

%description
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
* Thu Aug 09 2018 Dogtag PKI Team <pki-team@redhat.com> 1.2.0-0
- To list changes in <branch> since <tag>:
  $ git log --pretty=oneline --abbrev-commit --no-decorate <tag>..<branch>
