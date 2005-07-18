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

MCOM_ROOT = ../../../../../../..

include $(MCOM_ROOT)/netsite/nsdefs.mk
include $(MCOM_ROOT)/netsite/nsconfig.mk
include $(MCOM_ROOT)/netsite/console/rules.mk

DEST_DIR=$(NMC_DIR)/client/ace

source = \
	$(DEST_DIR)/ace.properties \
	$(DEST_DIR)/ACIAttribute.class \
	$(DEST_DIR)/ACIEditor.class \
	$(DEST_DIR)/ACIManager.class \
	$(DEST_DIR)/ACIParser.class \
	$(DEST_DIR)/HostTab.class \
	$(DEST_DIR)/IACITab.class \
	$(DEST_DIR)/RightsTab.class \
	$(DEST_DIR)/TargetTab.class \
	$(DEST_DIR)/TimeTab.class \
	$(DEST_DIR)/UGChooserDialog.class \
	$(DEST_DIR)/UGTab.class \
	$(DEST_DIR)/UGTable.class \
	$(DEST_DIR)/UGTableModel.class

all: $(DEST_DIR) $(source)

test: $(DEST_DIR)/ACITest.class
	java -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)" com.netscape.management.client.ace.ACITest

include $(MCOM_ROOT)/netsite/console/targets.mk
