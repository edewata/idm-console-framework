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

DEST_DIR=$(NMC_DIR)/client/acleditor

source = \
		$(DEST_DIR)/ACLEditorConstants.class \
		$(DEST_DIR)/DataModelAdapter.class \
		$(DEST_DIR)/HorizontalLine.class \
		$(DEST_DIR)/CallbackAction.class \
		$(DEST_DIR)/SelectionListener.class \
		$(DEST_DIR)/CustomJTable.class \
		$(DEST_DIR)/ACLEditorWindow.class \
		$(DEST_DIR)/PopupErrorDialog.class \
		$(DEST_DIR)/WindowFactory.class \
		$(DEST_DIR)/Table.class \
		$(DEST_DIR)/UserGroupDataModel.class \
		$(DEST_DIR)/HostsDataModel.class \
		$(DEST_DIR)/RightsDataModel.class \
		$(DEST_DIR)/PickerWindow.class \
		$(DEST_DIR)/RightsWindow.class \
		$(DEST_DIR)/TimeWindow.class \
		$(DEST_DIR)/SyntaxWindow.class \
		$(DEST_DIR)/AttributesWindow.class \
		$(DEST_DIR)/ACLSelectorWindow.class \
		$(DEST_DIR)/DataModelFactory.class \
		$(DEST_DIR)/TableDataModel.class \
		$(DEST_DIR)/InheritedTableDataModel.class \
		$(DEST_DIR)/DefaultDataModelFactory.class \
		$(DEST_DIR)/DefaultWindowFactory.class \
		$(DEST_DIR)/ACLRuleTableWindow.class \
		$(DEST_DIR)/ACLEditor.class \
		$(DEST_DIR)/ACLResources.properties

all: $(DEST_DIR) $(DEST_DIR)/images $(source)

include $(MCOM_ROOT)/netsite/console/targets.mk
