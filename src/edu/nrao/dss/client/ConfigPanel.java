package edu.nrao.dss.client;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FillLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

public class ConfigPanel extends VerticalPanel {
	private static ArrayList<Label> labels = new ArrayList<Label>();

	// Main container
	public ConfigPanel() {
		FieldSet configPanel = new FieldSet();
		configPanel.setHeading("Configuration");
		configPanel.add(getGeneral());
		configPanel.add(getHardware());
		configPanel.add(getSource());
		setLayout(new FitLayout());
		add(configPanel);
		// add(getView());
		// update();
	}

	// General container
	public FieldSet getGeneral() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(addField("conversion", "Conversion:"));
		vp.add(addField("time", "Time:"));
		vp.add(addField("trimester", "Trimester:"));
		vp.add(addField("sensitivity", "Sensitivity:"));
		FieldSet fs = new FieldSet();
		fs.setHeading("General");
		fs.setLayout(new FillLayout());
		fs.setCollapsible(true);
		fs.add(vp);
		return fs;
	}

	// Hardware container
	public FieldSet getHardware() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(addField("backend", "Backend:"));
		vp.add(addField("mode", "Mode:"));
		vp.add(addField("receiver", "Receiver:"));
		vp.add(addField("beams", "Beams:"));
		vp.add(addField("polarization", "Polarization:"));
		FieldSet fs = new FieldSet();
		fs.setHeading("Hardware Information");
		fs.setLayout(new FillLayout());
		fs.setCollapsible(true);
		fs.add(vp);
		return fs;
	}

	// Source container
	public FieldSet getSource() {
		VerticalPanel vp = new VerticalPanel();
		vp.add(addField("sourceDec", "Source Declination:"));
		vp.add(addField("frame", "Frame:"));
		vp.add(addField("minElev", "Minimum Elevation:"));
		vp.add(addField("restFreq", "Rest Frequency:"));
		FieldSet fs = new FieldSet();
		fs.setHeading("Source Information");
		fs.setLayout(new FillLayout());
		fs.setCollapsible(true);
		fs.add(vp);
		return fs;
	}

	// Data Reduction container
	public FieldSet getData() {
		// TODO: setup data container after data tab is done
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label("Average Orthogonal Polarizations"));
		vp.add(new Label("Difference Signals"));
		vp.add(addField("sourceDec", "Source Declination:"));
		vp.add(addField("frame", "Frame:"));
		vp.add(addField("minElev", "Minimum Elevation:"));
		vp.add(addField("restFreq", "Rest Frequency:"));
		FieldSet fs = new FieldSet();
		fs.setHeading("Data Reduction");
		fs.setLayout(new FillLayout());
		fs.setCollapsible(true);
		fs.add(vp);
		return fs;
	}

	// cuts out redundant code
	private Label addField(String name, String label) {
		Label lbl = new Label(label);
		lbl.setData("label", label);
		lbl.setData("name", name);
		labels.add(lbl);
		return lbl;
	}

	// TODO
	// update()
	// it would be nice to get rid of this, by making these labels
	// observers of the Result store instead of updating 1 at a time
	/*
	 * public static void updates() { for (Label lbl : labels) { Field question
	 * = Test.getQuestion((String) lbl.getData("name")); lbl.setText((String)
	 * lbl.getData("label") + "  " + question.getValue());
	 * GWT.log("CONFIGPANEL: adding " + (String) lbl.getData("name") +
	 * " with value " + question.getValue()); } }
	 * 
	 * public ListView<Result> geetView() {
	 * 
	 * ListView<Result> view = new ListView<Result>();
	 * view.setTemplate(getTemplate());
	 * view.setStore(ResultStore.getResultStore());
	 * 
	 * return view; }
	 */

}
