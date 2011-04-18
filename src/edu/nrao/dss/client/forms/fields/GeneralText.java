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
