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

package edu.nrao.dss.client.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

import edu.nrao.dss.client.forms.fields.GeneralCombo;

public class HardwareForm extends BasicForm {
	private GeneralCombo backendCombo, modeCombo, receiverCombo;
	private GeneralCombo beamCombo, polarizationCombo, bandwidthCombo;
	private GeneralCombo windowCombo, switchingCombo;
	
	public HardwareForm() {
		super("Hardware Information");
	}

	public void initLayout() {
		backendCombo      = new GeneralCombo("backend", "Backend");
		modeCombo         = new GeneralCombo("mode", "Mode");
		receiverCombo     = new GeneralCombo("receiver", "Receiver");
		beamCombo         = new GeneralCombo("beams", "Beams");
		polarizationCombo = new GeneralCombo("polarization", "Polarization");
		bandwidthCombo    = new GeneralCombo("bandwidth", "BandWidth (MHz)");
		windowCombo       = new GeneralCombo("windows", "Number of Spectral Windows");
		switchingCombo    = new GeneralCombo("switching", "Switching Mode");


		// sets combo stores
		initiateHardware();
		
		Listener<FieldEvent> hwchanger = new Listener<FieldEvent> (){
			public void handleEvent(FieldEvent fe) {
				HashMap<String, Object> selected = getSelected();
				if (selected.get("backend") != null) {
					JSONRequest.post("/calculator/set_hardware", selected,
							new JSONCallbackAdapter() {
								public void onSuccess(JSONObject json) {
									parseJSON(json);
								}
							});
				}
			}
		};
		
		backendCombo.addListener(Events.Select,      hwchanger);
		modeCombo.addListener(Events.Select,         hwchanger);
		receiverCombo.addListener(Events.Select,     hwchanger);
		beamCombo.addListener(Events.Select,         hwchanger);
		polarizationCombo.addListener(Events.Select, hwchanger);
		bandwidthCombo.addListener(Events.Select,    hwchanger);
		windowCombo.addListener(Events.Select,       hwchanger);
		switchingCombo.addListener(Events.Select,    hwchanger);

		// attaching fields
		String instructions = "Answer questions from top to bottom.  If you change a question that was answered previously, check all answers that follow.  Some answers will dictate the answer for other questions.";
		add(new Label(instructions));
		add(backendCombo);
		add(modeCombo);
		add(receiverCombo);
		add(beamCombo);
		add(polarizationCombo);
		add(bandwidthCombo);
		add(windowCombo);
		add(switchingCombo);
	}

	public void validate() {

	}

	public void notify(String name, String value) {

	}

	private HashMap<String, Object> getSelected() {
		HashMap<String, Object> selected = new HashMap<String, Object>();
		selected.put(backendCombo.getName(), backendCombo.getValue());
		selected.put(modeCombo.getName(), modeCombo.getValue());
		selected.put(receiverCombo.getName(), receiverCombo.getValue());
		selected.put(beamCombo.getName(), beamCombo.getValue());
		selected.put(polarizationCombo.getName(), polarizationCombo.getValue());
		selected.put(bandwidthCombo.getName(), bandwidthCombo.getValue());
		selected.put(windowCombo.getName(), windowCombo.getValue());
		selected.put(switchingCombo.getName(), switchingCombo.getValue());
		return selected;
	}

	public HashMap<String, GeneralCombo> getCombos() {

		HashMap<String, GeneralCombo> hm = new HashMap<String, GeneralCombo>();
		hm.put(backendCombo.getName(), backendCombo);
		hm.put(modeCombo.getName(), modeCombo);
		hm.put(receiverCombo.getName(), receiverCombo);
		hm.put(beamCombo.getName(), beamCombo);
		hm.put(polarizationCombo.getName(), polarizationCombo);
		hm.put(bandwidthCombo.getName(), bandwidthCombo);
		hm.put(windowCombo.getName(), windowCombo);
		hm.put(switchingCombo.getName(), switchingCombo);
		return hm;

	}

	public GeneralCombo getCombo(String name) {
		try {
			return getCombos().get(name);
		} catch (Exception e) {

			return new GeneralCombo();
		}

	}
	private void parseJSON(JSONObject json) {
		if (json.get("success").isString().stringValue()
				.toString().equals("ok")) {
		} else {
			// TODO: return error notification to user
			return;
		}

		for (Iterator<String> i = json.keySet().iterator(); i.hasNext();) {
			String key = i.next();
			ArrayList<String> options = new ArrayList<String>();
			if (!key.equals("success")) {
				if (key.equals("backend")) {
					options.add("");
				}
				
				JSONArray values = json.get(key).isArray();
				for (int x = 0; x < values.size(); x++) {
					options.add(values.get(x).toString());
				}
				getCombo(key).setComboStore(options);
			}
		}
		notifyAllForms();
		//ResultsData.fetchResults();
	}

	public void initiateHardware() {
		// gets the initial store for the combo boxes
		JSONRequest.get("/calculator/initiate_hardware",
				new JSONCallbackAdapter() {
					public void onSuccess(JSONObject json) {
						parseJSON(json);
					}
				});
	}
}
