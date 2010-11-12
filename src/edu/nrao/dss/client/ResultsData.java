package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class ResultsData {
	public static List<Result> results = new ArrayList<Result>();
	
	public static List<Result> getResults() {
		return results;
	}
	
	public static String getString(JSONValue value) {
		JSONString svalue = value.isString();
		JSONNumber nvalue = value.isNumber();
		if (svalue != null) {
			return svalue.stringValue();
		} else if (nvalue != null) {
			return nvalue.doubleValue() + "";
		}
		return null;
	}
	
    public static void fetchResults() {
    	JSONRequest.get("/calculator/get_result/", new HashMap<String, Object>(), 
            new JSONCallbackAdapter() {
			    public void onSuccess(JSONObject json) {   
			    	if (json.get("success").isString().stringValue().toString().equals("ok")) {
			    		ResultsData.results.clear(); // ok to proceed
			    	} else {
			    		// TODO: return error notification to user
			    		return;
			    	}

			    	for (Iterator<String> i = json.keySet().iterator(); i.hasNext();) {
			    		String key = i.next();
			    		if (!key.equals("success")) {
				    		JSONArray values = json.get(key).isArray();
				    		results.add(new Result(key
				    				             , ResultsData.getString(values.get(0))
				    				             , ResultsData.getString(values.get(1))
				    				             //, ResultsData.getString(values.get(2))
				    				             ));			    			
			    		}
			    	}
			    	ResultStore.getResultStore().update();
			    }
		    }
    	);
    }

    
    public static void intiateHardware(final HashMap<String, GeneralCombo> combos) {
        //public static void fetchHardware(HashMap<String, Object> selected, GeneralCombo hardwareCombo) {
        	JSONRequest.get("/calculator/test_hardware/", 
                new JSONCallbackAdapter() {
    			    public void onSuccess(JSONObject json) {   
    			    	ListStore<ComboModel> tempStore = new ListStore<ComboModel>();
    			    	if (json.get("success").isString().stringValue().toString().equals("ok")) {
    			    		// ok to proceed
    			    	} else {
    			    		// TODO: return error notification to user
    			    		return;
    			    	}
    			    	for (Iterator<String> i = json.keySet().iterator(); i.hasNext();) {
    			    		String key = i.next();
    			    		if (!key.equals("success")) {
    			    			JSONArray values = json.get(key).isArray();
    				    		tempStore.removeAll();
    				    		
    				    		//combos.get(key).setStore(tempStore);
    				    		//backends.add(new ComboModel(key, values.get(0).toString(), values.get(1).toString(), values.get(2).toString()));			    			
    			    		}
    			    	}
    			    	//ResultStore.getResultStore().update();
    			    }
    		    }
        	);
        }
    
    
    public static void resetResults() {
    	JSONRequest.get("/calculator/reset/", new HashMap<String, Object>(), 
            new JSONCallbackAdapter() {
			    public void onSuccess(JSONObject json) {   
			    	if (json.get("success").isString().stringValue().toString().equals("ok")) {
			    		ResultsData.results.clear(); // ok to proceed
			    	} else {
			    		// TODO: return error notification to user
			    		return;
			    	}

			    	for (Iterator<String> i = json.keySet().iterator(); i.hasNext();) {
			    		String key = i.next();
			    		if (!key.equals("success")) {
				    		JSONArray values = json.get(key).isArray();
				    		//results.add(new Result(key, values.get(0).toString(), values.get(1).toString(), values.get(2).toString()));			    			
				    		results.add(new Result(key
	    				             , ResultsData.getString(values.get(0))
	    				             , ResultsData.getString(values.get(1))
	    				             //, ResultsData.getString(values.get(2))
	    				             ));
			    		}
			    	}
			    	ResultStore.getResultStore().update();
			    }
		    }
    	);
    }
}
