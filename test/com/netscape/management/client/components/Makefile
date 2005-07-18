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

MCOM_ROOT = ../../../../../../../..

include $(MCOM_ROOT)/netsite/nsdefs.mk
include $(MCOM_ROOT)/netsite/nsconfig.mk
include $(MCOM_ROOT)/netsite/console/rules.mk

DEST_DIR=$(NMC_DIR)/client/components/test

SOURCE_FILES=$(subst .java,,$(wildcard *Test.java))
CLASS_FILES=$(addsuffix .class, $(SOURCE_FILES))
DEST_FILES=$(addprefix $(DEST_DIR)/, $(CLASS_FILES))
FIRST_SOURCE_FILE=$(firstword $(SOURCE_FILES))

all:
	@echo Usage Syntax:
	@$(addprefix echo ' gmake , $(addsuffix ';, $(SOURCE_FILES)))
	@echo 
	@echo Example:
	@echo ' gmake $(FIRST_SOURCE_FILE)'
	@echo will compile $(FIRST_SOURCE_FILE).java and run $(FIRST_SOURCE_FILE).class

%Test:
	gmake $(addprefix $(DEST_DIR)/, $(addsuffix .class, $@))
	gmake RUNTARGET=com.netscape.management.client.components.test.$@ run

buildAll: $(DEST_DIR) $(DEST_DIR)/images $(DEST_FILES)

include $(MCOM_ROOT)/netsite/console/targets.mk
