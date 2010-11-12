package edu.nrao.dss.client;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;

public class GeneralText extends TextField<String> {

	GeneralText(String name, String label){
		setName(name);
		setData("name", name);
		setFieldLabel(label);
		getMessages().setRegexText("Invalid format");
		setAllowBlank(false);
	}

	//Validates the field with the given Regular expression and sets
	//the error message for invalid format
	public void setRegex(String regEx, String errorMsg){
		this.setRegex(regEx);
		this.getMessages().setRegexText(errorMsg);
	}
}
