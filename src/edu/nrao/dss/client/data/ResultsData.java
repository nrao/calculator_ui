package edu.nrao.dss.client.data;


public class ResultsData {
	
	private static DataGrid results = 
		new DataGrid("Results", new DataType("results", "total_results"));
	
	public static DataGrid getResults() {
		return results;
	}
	
	public static void loadResults() {
		results.load();
	}

}
