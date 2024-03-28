################################################################################
Name:             idm-console-framework
################################################################################

%global           product_id dogtag-console-framework

# Upstream version number:
%global           major_version 2
%global           minor_version 2
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

BuildArch:        noarch
%if 0%{?fedora}
ExclusiveArch:    %{java_arches} noarch
%endif

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
BuildRequires:    maven-local
BuildRequires:    ant >= 1.6.2
BuildRequires:    mvn(org.dogtagpki.jss:jss-base) >= 5.5.0
BuildRequires:    mvn(org.dogtagpki.ldap-sdk:ldapjdk) >= 5.5.0

%description
A Java Management Console framework used for remote server management.

################################################################################
%package -n %{product_id}
################################################################################

Summary:          Identity Management Console Framework

Requires:         %{java_headless}
Requires:         mvn(org.dogtagpki.jss:jss-base) >= 5.5.0
Requires:         mvn(org.dogtagpki.ldap-sdk:ldapjdk) >= 5.5.0

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

# flatten-maven-plugin is not available in RPM
%pom_remove_plugin org.codehaus.mojo:flatten-maven-plugin

# specify Maven artifact locations
%mvn_file org.dogtagpki.console-framework:console-framework \
    idm-console-framework/idm-console-framework \
    idm-console-framework \
    idm-console-base \
    idm-console-mcc \
    idm-console-mcc_en \
    idm-console-nmclf \
    idm-console-nmclf_en

################################################################################
%build
################################################################################

export JAVA_HOME=%{java_home}

# build without Javadoc
%mvn_build -j

################################################################################
%install
################################################################################

%mvn_install

################################################################################
%files -n %{product_id} -f .mfiles
################################################################################

%doc LICENSE

################################################################################
%changelog
* Thu Aug 09 2018 Dogtag PKI Team <pki-team@redhat.com> 1.2.0-0
- To list changes in <branch> since <tag>:
  $ git log --pretty=oneline --abbrev-commit --no-decorate <tag>..<branch>
