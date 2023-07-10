################################################################################
Name:             idm-console-framework
################################################################################

%global           product_id dogtag-console-framework

# Upstream version number:
%global           major_version 2
%global           minor_version 1
%global           update_version 0

# Downstream release number:
# - development/stabilization (unsupported): 0.<n> where n >= 1
# - GA/update (supported): <n> where n >= 1
%global           release_number 0.1

# Development phase:
# - development (unsupported): alpha<n> where n >= 1
# - stabilization (unsupported): beta<n> where n >= 1
# - GA/update (supported): <none>
%global           phase alpha1

%undefine         timestamp
%undefine         commit_id

Summary:          Identity Management Console Framework
URL:              https://github.com/dogtagpki/idm-console-framework
License:          LGPLv2
Version:          %{major_version}.%{minor_version}.%{update_version}
Release:          %{release_number}%{?phase:.}%{?phase}%{?timestamp:.}%{?timestamp}%{?commit_id:.}%{?commit_id}%{?dist}

BuildArch:        noarch

# To create a tarball from a version tag:
# $ git archive \
#     --format=tar.gz \
#     --prefix idm-console-framework-<version>/ \
#     -o idm-console-framework-<version>.tar.gz \
#     <version tag>
Source: https://github.com/dogtagpki/idm-console-framework/archive/v%{version}%{?phase:-}/idm-console-framework-%{version}%{?phase:-}%{?phase}.tar.gz

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
Provides:         idm-console-framework = %{major_version}.%{minor_version}
%endif
Provides:         %{product_id} = %{major_version}.%{minor_version}

%description -n %{product_id}
A Java Management Console framework used for remote server management.

################################################################################
%prep
################################################################################

%autosetup -n idm-console-framework-%{version}%{?phase:-}%{?phase} -p 1

################################################################################
%build
################################################################################

./build.sh \
    %{?_verbose:-v} \
    --work-dir=%{_vpath_builddir} \
    dist

################################################################################
%install
################################################################################

mkdir -p %{buildroot}%{_javadir}

./build.sh \
    %{?_verbose:-v} \
    --work-dir=%{_vpath_builddir} \
    --java-dir=%{buildroot}%{_javadir} \
    install

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
