# BEGIN COPYRIGHT BLOCK
# Copyright (C) 2001 Sun Microsystems, Inc.  Used by permission.
# Copyright (C) 2005 Red Hat, Inc.
# All rights reserved.
#
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation version
# 2.1 of the License.
#                                                                                 
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#                                                                                 
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
# END COPYRIGHT BLOCK

override BUILD_MODULE=HTTP_ADMIN

NOSTDCLEAN=true

MCOM_ROOT = ../../../../../..
DIR_SUFFIX = sdk/examples

include ../../../../../nsdefs.mk
include ../../../../../nsconfig.mk
include ../../../../../admserv/client/rules.mk

DEST_DIR = $(CLASS_DIR)/supermail

source = \
		$(DEST_DIR)/SuperMailFrameworkInitializer.class \
		$(DEST_DIR)/SuperMailServer.class \
		$(DEST_DIR)/supermail.properties \
		$(DEST_DIR)/classes.env

all: $(DEST_DIR) do_ug do_topology do_customview do_configuration do_status do_images $(source)

run: all
	$(JAVA) -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)" supermail.SuperMailServer

clean:
	rm -f $(DEST_DIR)/*.class;

$(DEST_DIR)/%.class : %.java
	$(JAVAC) -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)" -d $(CLASS_DIR) $<

$(DEST_DIR):	
	mkdir -p $(DEST_DIR)

do_images:
	cd images; $(MAKE) $(MAKEFLAGS)

do_ug:
	cd ug; $(MAKE)  $(MAKEFLAGS)

do_topology:
	cd topology; $(MAKE)  $(MAKEFLAGS)

do_customview:
	cd customview; $(MAKE)  $(MAKEFLAGS)

do_configuration: 
	cd configuration; $(MAKE)  $(MAKEFLAGS)

do_status:
	cd status; $(MAKE)  $(MAKEFLAGS)

$(DEST_DIR)/classes.env: classes.env
	cp $< $@

$(DEST_DIR)/%.properties: %.properties
	cp $< $@
