package edu.nrao.dss.client;

import com.google.gwt.core.client.GWT;

public class Functions {
	
	private static double c      = 3e8; // speed of light in m/s
	
	public static double velocity2Frequency(String doppler, double rfreq, double velocity, double redshift) {
		double tfreq = 0;
		if (doppler.equals("Redshift")) {
			tfreq = rfreq / (1 + redshift);
		} else if (doppler.equals("Radio")) {
			tfreq = rfreq * (1 - (velocity * 1e3) / c);
		} else {
			tfreq = rfreq / (1 + (velocity * 1e3) / c);
		}
		return tfreq;
	}

}
