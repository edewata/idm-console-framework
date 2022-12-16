#
# Copyright Red Hat, Inc.
#
# SPDX-License-Identifier: GPL-2.0-or-later
#

ARG BASE_IMAGE="registry.fedoraproject.org/fedora:latest"
ARG COPR_REPO="@pki/master"

################################################################################
FROM $BASE_IMAGE AS idm-console-framework-base

RUN dnf install -y systemd \
    && dnf clean all \
    && rm -rf /var/cache/dnf

CMD [ "/usr/sbin/init" ]

################################################################################
FROM idm-console-framework-base AS idm-console-framework-deps

ARG COPR_REPO

# Enable COPR repo if specified
RUN if [ -n "$COPR_REPO" ]; then dnf install -y dnf-plugins-core; dnf copr enable -y $COPR_REPO; fi

# Install IDM Console Framework runtime dependencies
RUN dnf install -y dogtag-console-framework \
    && dnf remove -y dogtag-* --noautoremove \
    && dnf clean all \
    && rm -rf /var/cache/dnf

################################################################################
FROM idm-console-framework-deps AS idm-console-framework-builder-deps

# Install build tools
RUN dnf install -y rpm-build

# Import IDM Console Framework sources
COPY idm-console-framework.spec /root/idm-console-framework/
WORKDIR /root/idm-console-framework

# Install IDM Console Framework build dependencies
RUN dnf builddep -y --spec idm-console-framework.spec

################################################################################
FROM idm-console-framework-builder-deps AS idm-console-framework-builder

# Import IDM Console Framework sources
COPY . /root/idm-console-framework/

# Build IDM Console Framework packages
RUN ./build.sh --work-dir=build rpm

################################################################################
FROM idm-console-framework-deps AS idm-console-framework-runner

# Import IDM Console Framework packages
COPY --from=idm-console-framework-builder /root/idm-console-framework/build/RPMS /tmp/RPMS/

# Install IDM Console Framework packages
RUN dnf localinstall -y /tmp/RPMS/* \
    && dnf clean all \
    && rm -rf /var/cache/dnf \
    && rm -rf /tmp/RPMS
