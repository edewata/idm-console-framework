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

CLASS_DIR=../../../../../..
DEST_DIR=$(CLASS_DIR)/netscape/management/client/util/test
DIR=.

#
# programs list
#
source = \
	$(DEST_DIR)/TestLinkedList.class 

all: $(DEST_DIR) $(source)

clean:
	rm $(DEST_DIR)/*.class;

fresh: $(clean) $(all)

javadoc:
	javadoc -d docs *.java netscape/management/client/util

cleandoc:
	rm docs/*.html

$(DEST_DIR)/%.class : %.java
	javac  -d $(DEST_DIR) $<

$(DEST_DIR):	
	mkdir -p $(DEST_DIR)
