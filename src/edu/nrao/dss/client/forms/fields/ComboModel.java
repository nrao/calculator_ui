// Copyright (C) 2011 Associated Universities, Inc. Washington DC, USA.
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
// 
// Correspondence concerning GBT software should be addressed as follows:
//       GBT Operations
//       National Radio Astronomy Observatory
//       P. O. Box 2
//       Green Bank, WV 24944-0002 USA

package edu.nrao.dss.client.forms.fields;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.google.gwt.json.client.JSONValue;

public class ComboModel extends BaseModel {
	public ComboModel(String name, String abbr) {
		set("name", name);
		set("abbr", abbr);
	}

	public ComboModel(String name) {
		set("name", name);
	}

	public ComboModel(JSONValue name) {
		set("name", name);
	}

	public String getName(String name) {

		return (String) get("name");
	}

	public String getValue(String value) {

		return (String) get("value");
	}

	public String toString() {
		return get("name");
	}

}
