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

override BUILD_MODULE=CONSOLE
NO_BUILD_NUM=true
NOSTDCLEAN=true

MCOM_ROOT = ../../../../../../..

include $(MCOM_ROOT)/netsite/nsdefs.mk
include $(MCOM_ROOT)/netsite/nsconfig.mk
include ../../../../../rules.mk
NOSTDCLEAN=true

DEST_DIR=$(NMC_DIR)/client/test

#
# programs list
#
source = \
		$(DEST_DIR)/DemoTaskModel.class \
		$(DEST_DIR)/DemoResourceModel.class \
		$(DEST_DIR)/DemoFrameworkInitializer.class

all: $(DEST_DIR) $(source) $(DEST_DIR)/icons

run: all
	$(JAVA) -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)$(PATH_SEP)$(DEST_DIR)" DemoFrameworkInitializer

clean:
	rm $(DEST_DIR)/*.class;

fresh: $(clean) $(all)

$(DEST_DIR)/%.class : %.java
	$(JAVAC) -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)$(PATH_SEP)$(CLASS_DIR)/com/netscape/management/client/test" -d $(DEST_DIR) $<

$(DEST_DIR):	
	mkdir -p $(DEST_DIR)

$(DEST_DIR)/icons:
	mkdir $(DEST_DIR)/icons
	-cp icons/* $(DEST_DIR)/icons/*
