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

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;

public class GeneralText extends TextField<String> {

	public GeneralText(String name, String label){
		setName(name);
		setData("name", name);
		setFieldLabel(label);
		getMessages().setRegexText("Invalid format");
		setAllowBlank(false);
		addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent be) {
				getElement().getParentElement().addClassName("x-grid3-dirty-cell");
				
			}
			
		});
	}

	//Validates the field with the given Regular expression and sets
	//the error message for invalid format
	public void setRegex(String regEx, String errorMsg){
		this.setRegex(regEx);
		this.getMessages().setRegexText(errorMsg);
	}
}
