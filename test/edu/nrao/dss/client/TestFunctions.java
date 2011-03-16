package edu.nrao.dss.client;

import com.google.gwt.junit.client.GWTTestCase;

public class TestFunctions extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "edu.nrao.dss.Calculator_ui";
	}
	
	public void testC() {
		assertEquals(2.99792458e8, Functions.getC());
	}
	
	public void testVelocity2Frequency() {
		double freq = Functions.velocity2Frequency("Redshift", 1, 1, 1);
		assertEquals(0.5, freq);
	}


}
