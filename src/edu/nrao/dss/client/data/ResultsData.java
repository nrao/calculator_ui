package edu.nrao.dss.client.data;



public class ResultsData {
	
	private static DataGrid resultsGrid = 
		new DataGrid("Results", new DataType("results", "total_results"));
	
	public static DataGrid getResultsGrid() {
		return resultsGrid;
	}
	
	
	public static void loadResults() {
		resultsGrid.load();
	}

}
