package edu.nrao.dss.client;

import com.google.gwt.junit.client.GWTTestCase;

public class TestFunctions extends GWTTestCase {

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "edu.nrao.dss.Calculator_ui";
	}
	
	public void testC() {
		assertEquals(2.99792458e8, Functions.getC());
	}


}
