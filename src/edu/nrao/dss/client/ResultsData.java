package edu.nrao.dss.client;

public class ResultsData {
	
	private static ResultsGrid results = new ResultsGrid();
	
	public static ResultsGrid getResults() {
		return results;
	}
	
	public static void loadResults() {
		results.load();
	}

}
