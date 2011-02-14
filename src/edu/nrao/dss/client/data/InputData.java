package edu.nrao.dss.client.data;


public class InputData {
	
	private static DataGrid inputGrid = 
		new DataGrid("User Input", new DataType("input", "total_input"));
	
	public static DataGrid getInputGrid() {
		return inputGrid;
	}
	
	public static void loadInput() {
		inputGrid.load();
	}

}
