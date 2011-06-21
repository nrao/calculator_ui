// Copyright (C) 2011 Associated Universities, Inc. Washington DC, USA.
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
// 
// Correspondence concerning GBT software should be addressed as follows:
//       GBT Operations
//       National Radio Astronomy Observatory
//       P. O. Box 2
//       Green Bank, WV 24944-0002 USA

package edu.nrao.dss.client;

import com.google.gwt.core.client.GWT;

public class Functions {
	
	private static double c      = 2.99792458e8; // speed of light in m/s
	
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
	
	public static double getC() {
		return c;
	}

}
