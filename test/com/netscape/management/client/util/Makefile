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

DEST_DIR=$(NMC_DIR)/client/util

source = \
	$(DEST_DIR)/SimpleReferral.class \
	$(DEST_DIR)/LinkedListElement.class \
	$(DEST_DIR)/LinkedList.class \
	$(DEST_DIR)/Debug.class \
	$(DEST_DIR)/Permissions.class \
	$(DEST_DIR)/IProgressListener.class \
	$(DEST_DIR)/SingleByteDocument.class \
	$(DEST_DIR)/SingleBytePasswordField.class \
	$(DEST_DIR)/SingleByteTextField.class \
	$(DEST_DIR)/SingleByteTextArea.class \
	$(DEST_DIR)/UITools.class \
	$(DEST_DIR)/ImageInfo.class \
	$(DEST_DIR)/URLByteEncoder.class \
	$(DEST_DIR)/ExtendedMouseAdapter.class \
	$(DEST_DIR)/ModalDialogUtil.class \
	$(DEST_DIR)/ArrowIcon.class \
	$(DEST_DIR)/AbstractDialog.class \
	$(DEST_DIR)/AbstractModalDialog.class \
	$(DEST_DIR)/TableHeaderEditor.class \
	$(DEST_DIR)/DefaultCellRenderer.class \
	$(DEST_DIR)/KingpinLDAPConnection.class \
	$(DEST_DIR)/JButtonFactory.class \
	$(DEST_DIR)/GridBagUtil.class \
	$(DEST_DIR)/UtilConsoleGlobals.class \
	$(DEST_DIR)/ResourceSet.class \
	$(DEST_DIR)/Browser.class \
	$(DEST_DIR)/RemoteImage.class \
	$(DEST_DIR)/AssertionException.class \
	$(DEST_DIR)/AssertionError.class \
	$(DEST_DIR)/Assert.class \
	$(DEST_DIR)/ResourceSet.class \
	$(DEST_DIR)/LDAPUtil.class \
	$(DEST_DIR)/AdmTaskArg.class \
	$(DEST_DIR)/AdmTask.class \
	$(DEST_DIR)/ClipBoard.class \
	$(DEST_DIR)/SwingPackageNameConverter.class \
	$(DEST_DIR)/KingpinClassLoader.class \
	$(DEST_DIR)/LocalJarClassLoader.class \
	$(DEST_DIR)/ClassLoaderUtil.class \
	$(DEST_DIR)/BrowseHistoryListener.class \
	$(DEST_DIR)/BrowseHtmlHistory.class \
	$(DEST_DIR)/BrowseHtmlPane.class \
	$(DEST_DIR)/BrowseHtmlDialog.class \
	$(DEST_DIR)/Help.class \
	$(DEST_DIR)/AcceptLanguage.class \
	$(DEST_DIR)/AcceptLanguageList.class \
	$(DEST_DIR)/DottedBorder.class \
	$(DEST_DIR)/CompleteAcceptLanguageList.class \
	$(DEST_DIR)/default.properties \
	$(DEST_DIR)/LDAPInputStream.class \
	$(DEST_DIR)/LDAPOutputStream.class \
	$(DEST_DIR)/DottedBorder.class \
	$(DEST_DIR)/About.class \
	$(DEST_DIR)/IWizardPageControl.class \
	$(DEST_DIR)/IWizardControl.class \
	$(DEST_DIR)/Wizard.class \
	$(DEST_DIR)/TimePicker.class \
	$(DEST_DIR)/DatePicker.class \
	$(DEST_DIR)/DateTimePicker.class \
	$(DEST_DIR)/MultilineLabel.class \
	$(DEST_DIR)/IndexDialog.class \
	$(DEST_DIR)/SpinControl.class \
	$(DEST_DIR)/SpinEvent.class \
	$(DEST_DIR)/ISpinListener.class \
	$(DEST_DIR)/DayPicker.class 

all: $(DEST_DIR) $(source)

testcl: all
	java -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)" com.netscape.management.client.util.LocalJarClassLoader

testdate: all
	java -classpath "$(CLASSPATH)$(PATH_SEP)$(CLASS_DIR)" com.netscape.management.client.util.DateTimePicker

include $(MCOM_ROOT)/netsite/console/targets.mk
