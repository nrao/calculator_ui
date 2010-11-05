package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;

public class HardwareForm extends BasicForm {
	private GeneralCombo backendCombo, modeCombo, receiverCombo;
	private GeneralCombo beamCombo, polarizationCombo, bandwidthCombo;
	private GeneralCombo windowCombo, switchingCombo, integrationCombo;
	private Button reset;

	public HardwareForm() {
		super("Hardware Information");
	}

	public void initLayout() {
		SelectionListener<ButtonEvent> resetHardware = new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				initiateHardware();
			}
		};

		backendCombo = new GeneralCombo("backend", "Backend");
		modeCombo = new GeneralCombo("mode", "Mode");
		receiverCombo = new GeneralCombo("receiver", "Receiver");
		beamCombo = new GeneralCombo("beams", "Beams");
		polarizationCombo = new GeneralCombo("polarization", "Polarization");
		bandwidthCombo = new GeneralCombo("bandwidth", "BandWidth");
		integrationCombo = new GeneralCombo("integration",
				"Minimum Allowable integration");
		windowCombo = new GeneralCombo("windows", "Number of spectrtal windows");
		switchingCombo = new GeneralCombo("switching", "Switching mode");


		// sets combo stores
		initiateHardware();
		reset = new Button("Reset");

		backendCombo.addListener(Events.Select, new HardwareChanger());
		modeCombo.addListener(Events.Select, new HardwareChanger());
		receiverCombo.addListener(Events.Select, new HardwareChanger());
		beamCombo.addListener(Events.Select, new HardwareChanger());
		polarizationCombo.addListener(Events.Select, new HardwareChanger());
		bandwidthCombo.addListener(Events.Select, new HardwareChanger());
		windowCombo.addListener(Events.Select, new HardwareChanger());
		integrationCombo.addListener(Events.Select, new HardwareChanger());
		switchingCombo.addListener(Events.Select, new HardwareChanger());

		reset.addSelectionListener(resetHardware);
		// attaching fields
		this.add(backendCombo);
		this.add(modeCombo);
		this.add(receiverCombo);
		this.add(beamCombo);
		this.add(polarizationCombo);
		this.add(bandwidthCombo);
		this.add(windowCombo);
		this.add(integrationCombo);
		this.add(switchingCombo);
		this.add(reset);
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
		selected.put(integrationCombo.getName(), integrationCombo.getValue());
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
		hm.put(integrationCombo.getName(), integrationCombo);
		hm.put(switchingCombo.getName(), switchingCombo);
		return hm;

	}

	public GeneralCombo getCombo(String name) {
		try {
			return getCombos().get(name);
		} catch (Exception e) {

			GWT.log("GetCombo Error " + e.getMessage());
			return new GeneralCombo();
		}

	}

	public void initiateHardware() {
		// gets the initial store for the combo boxes
		JSONRequest.get("/calculator/initiate_hardware",
				new JSONCallbackAdapter() {
					public void onSuccess(JSONObject json) {
						if (json.get("success").isString().stringValue()
								.toString().equals("ok")) {
						} else {
							// TODO: return error notification to user
							return;
						}
						for (Iterator<String> i = json.keySet().iterator(); i
								.hasNext();) {
							String key = i.next();
							ArrayList<String> options = new ArrayList<String>();
							if (!key.equals("success")) {
								JSONArray values = json.get(key).isArray();
								for (int x = 0; x < values.size(); x++) {
									GWT.log(x + ":" + values.get(x).isString());
									options.add(values.get(x).isString()
											.stringValue());
								}
								getCombo(key).initiateComboStore(options);
							}
						}
						notifyAllForms();
						//ResultsData.fetchResults();
					}
				});
	}



	class HardwareChanger implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			HashMap<String, Object> selected = getSelected();
			JSONRequest.post("/calculator/set_hardware", selected,
					new JSONCallbackAdapter() {
						public void onSuccess(JSONObject json) {
							if (json.get("success").isString().stringValue()
									.toString().equals("ok")) {

							} else {
								// TODO: return error notification to user
								return;
							}

							for (Iterator<String> i = json.keySet().iterator(); i
									.hasNext();) {
								String key = i.next();
								ArrayList<String> options = new ArrayList<String>();
								if (!key.equals("success")) {
									JSONArray values = json.get(key).isArray();
									for (int x = 0; x < values.size(); x++) {
										GWT.log(x + ":>"
												+ values.get(x).isString());
										options.add(values.get(x).toString());
									}
									getCombo(key).setComboStore(options);
								}
							}
							notifyAllForms();
							//ResultsData.fetchResults();
						}
					});
		}
	}

}
