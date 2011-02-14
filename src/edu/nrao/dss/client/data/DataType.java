package edu.nrao.dss.client.data;

import com.extjs.gxt.ui.client.data.ModelType;

public class DataType extends ModelType{
	
	public DataType(String root, String totalName) {
		setRoot(root);
		setTotalName(totalName);
		
		addField("term");
		addField("value");
		addField("units");
		addField("equation");
		addField("label");
		addField("display");
	}

}
