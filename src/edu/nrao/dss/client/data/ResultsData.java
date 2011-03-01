package edu.nrao.dss.client.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestException;



public class ResultsData {
	
	private static DataGrid resultsGrid = 
		new DataGrid("Results", new DataType("results", "total_results"));
	private static RequestBuilder rb = 
		new RequestBuilder(RequestBuilder.GET, "/calculator/results");
	
	public static DataGrid getResultsGrid() {
		return resultsGrid;
	}
	
	
	public static void loadResults() {
		resultsGrid.load();
		try {
			rb.send();
		} catch (RequestException e) {
		}
	}
	
	public static RequestBuilder getResultsRB() {
		return rb;
	}

}
