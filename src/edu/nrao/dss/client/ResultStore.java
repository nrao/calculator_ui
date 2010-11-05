package edu.nrao.dss.client;

import com.extjs.gxt.ui.client.store.ListStore;

public class ResultStore extends ListStore<Result> {
    private ResultStore ()
    {
    	// singleton
    }
    
    public static ResultStore getResultStore() {
    	if (singleton == null) {
    		singleton = new ResultStore();
    	}
    	return singleton;
    }

    public void update() {
    	removeAll();
    	add(ResultsData.getResults());
    }
    
    private static ResultStore singleton;
}


