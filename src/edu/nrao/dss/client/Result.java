package edu.nrao.dss.client;

import com.extjs.gxt.ui.client.data.BaseModel;

public class Result extends BaseModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Result() {
    }
    
    public Result(String term, String value, String units, String equation) {
    	set("term", term);
    	set("value", value);
    	set("units", units);
    	set("equation", equation);
    }
    
    public String getName() {
    	return (String) get("term");
    }
    
    public String getValue() {
    	return (String) get("value");
    }

    public String getEquation() {
    	return (String) get("equation");
    }
    
    public String getUnits() {
    	return (String) get("units");
    }

    public String toString() {
    	return getName();
    }
}
