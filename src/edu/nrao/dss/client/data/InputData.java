package edu.nrao.dss.client.data;


public class InputData {
	
	private static DataGrid input = 
		new DataGrid("Input", new DataType("input", "total_input"));
	
	public static DataGrid getInput() {
		return input;
	}
	
	public static void loadInput() {
		input.load();
	}

}
