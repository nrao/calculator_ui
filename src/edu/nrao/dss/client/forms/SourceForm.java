package edu.nrao.dss.client.forms;

import java.util.ArrayList;
import java.util.Arrays;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SliderEvent;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FillData;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;


public class SourceForm extends BasicForm {
	private GeneralText sourceDec, restFreq, sourceVelocity, redshift,
			topoFreq, rightAscension, tBG;
	private GeneralCombo doppler;
	private RadioGroup galactic, frame;
	private Slider diameter;
	private LabelField diameter_display;
	private double c      = 2.99792458e10; // speed of light in cm/s
	
	
	public SourceForm() {
		super("Source Information");
	}

	public void initLayout() {
		setCollapsible(true);
		sourceDec = new GeneralText("declination", "Est. Source Declination (SDD:MM)");
		sourceDec.setValue("38:26");
		
		restFreq  = new GeneralText("rest_freq", "Rest Frequency (MHz)");
		restFreq.setValue("0");
		topoFreq  = new GeneralText("topocentric_freq", "Topocentric Frequency (MHz)");
		topoFreq.setValue("0");
		doppler   = new GeneralCombo("doppler", "Doppler Correction", getDoppler());
		doppler.setValue(new ComboModel("Optical"));
		sourceVelocity = new GeneralText("source_velocity", "Source Velocity (km/s)");
		sourceVelocity.setValue("0");
		redshift = new GeneralText("redshift", "Redshift");
		redshift.setValue("0");
		
		frame = new RadioGroup("frame");
		frame.setFieldLabel("Frequency Specified in the");
		frame.setName("frame");
		frame.setId("frame");
		frame.setOrientation(Orientation.VERTICAL);
		
		Radio choice = new Radio();
		choice.setBoxLabel("Topocentric Frame");
		choice.setValueAttribute("Topocentric Frame");
		choice.setName("topocentric_frame");
		choice.setLabelSeparator("");
		frame.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Rest Frame");
		choice.setValueAttribute("Rest Frame");
		choice.setName("rest_frame");
		choice.setLabelSeparator("");
		choice.setValue(true);
		frame.add(choice);
		
		galactic = new RadioGroup("galactic");
		galactic.setFieldLabel("Source Contribution to System Temperature");
		galactic.setName("galactic");
		galactic.setId("galactic");
		galactic.setOrientation(Orientation.VERTICAL);
		
		choice = new Radio();
		choice.setBoxLabel("No Correction");
		choice.setToolTip("Assume there is no contribution from the source to Tsys.");
		choice.setValueAttribute("no_correction");
		choice.setName("no_correction");
		choice.setLabelSeparator("");
		choice.setValue(true);
		galactic.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("User Estimated Correction");
		choice.setToolTip("Enter a value for the expected contribution from the source to Tsys.");
		choice.setValueAttribute("estimated");
		choice.setName("estimated");
		choice.setLabelSeparator("");
		galactic.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Internal Galactic Model");
		choice.setToolTip("Use a model of the galactic contribution to Tsys.");
		choice.setValueAttribute("model");
		choice.setName("model");
		choice.setLabelSeparator("");
		galactic.add(choice);
		
		diameter_display = new LabelField("0.0");
		diameter_display.setFieldLabel("Source Diameter (arc minutes)");
		diameter_display.setLabelSeparator(":");
		
		diameter = new Slider();
		diameter.setMinValue(0);
		diameter.setMaxValue(12);
		diameter.setValue(0);
		diameter.setIncrement(1);
		diameter.setUseTip(false);
		
		final SliderField sf = new SliderField(diameter);
		sf.setLabelSeparator("");
		sf.setName("source_diameter_slider");
		sf.setId("source_diameter_slider");
		
		rightAscension = new GeneralText("right_ascension", "Approx Right Ascension (HH:MM)");
		rightAscension.setMessageTarget("side");
		rightAscension.setValidator(new Validator () {

			@Override
			public String validate(Field<?> field, String value) {
				if (value.isEmpty() || value.equals("-")) {
					return null;
				}
				
				String[] values = value.split(":");
				if (values.length < 2) {
					return null;
				}
				
				if (Float.valueOf(values[1]) >= 60.0) {
					return "Minutes cannot be greater than or equal to 60.";
				} else if (Float.valueOf(values[0]) < 0.0){
					return "Right Ascension cannot be negative.";
				}
				return null;
			}
			
		});
		
		tBG            = new GeneralText("estimated_continuum", "Contribution (K)");
		tBG.setToolTip("Enter contribution of source to continuum level in units of K.");
		tBG.setValue("0");
        
		sourceDec.setMessageTarget("side");
		sourceDec.setValidator(new Validator () {

			@Override
			public String validate(Field<?> field, String value) {
				if (value.isEmpty() || value.equals("-")) {
					return null;
				}
				
				String[] values = value.split(":");
				if (values.length < 2) {
					return null;
				}
				
				float min_el = (float) 0.0;
				float dec = (float) (Float.valueOf(values[0]).floatValue() + Float.valueOf(values[1]).floatValue() / 60.0);
				if (dec + min_el <= -51.57) {
					return "Declination " + value + "does not achieve the desired minimum elevation for the chosen receiver and frequency.";
				} else if (Float.valueOf(values[1]) >= 60.0) {
					return "Minutes cannot be greater than or equal to 60.";
				}
				return null;
			}
			
		});
		
		topoFreq.hide();
		topoFreq.setAllowBlank(true);
		redshift.hide();
		redshift.setAllowBlank(true);
		rightAscension.hide();
		rightAscension.setAllowBlank(true);
		tBG.hide();
		tBG.setAllowBlank(true);

		initListeners();

		FormData fd = new FormData(60, 20);
		 
		// attaching fields
		add(frame);
		add(restFreq, fd);
		add(topoFreq, fd);
		add(doppler);
		add(redshift, fd);
		add(sourceVelocity, fd);
		add(diameter_display);
		add(sf);
		
		FieldSet fs = new FieldSet();
		fs.setHeading("Source Contribution Corrections");
		FormLayout layout = new FormLayout();  
		layout.setLabelWidth(200);  
		fs.setLayout(layout);
		
		fs.add(galactic);
		fs.add(rightAscension, fd);
		fs.add(tBG, fd);
		add(fs);
		
		add(sourceDec, fd);
		
	}
	
	private void initListeners() {
		diameter.addListener(Events.Change, new Listener<SliderEvent> () {

			@Override
			public void handleEvent(SliderEvent se) {
				calcDiameter();
			}
		});
		frame.addListener(Events.Change, new HandleFrame());
		frame.addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent se) {
				calcDiameter();
				notifyAllForms();
			}
		});
		doppler.addListener(Events.Select, new HandleDoppler());
		doppler.addListener(Events.Select, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent se) {
				calcDiameter();
				notifyAllForms();
			}
		});
		galactic.addListener(Events.Change, new HandleGalactic());
		restFreq.addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent se) {
				calcDiameter();
				notifyAllForms();
			}
		});
		topoFreq.addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent se) {
				calcDiameter();
				notifyAllForms();
			}
		});
		redshift.addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent se) {
				calcDiameter();
				notifyAllForms();
			}
		});
		sourceVelocity.addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent se) {
				calcDiameter();
				notifyAllForms();
			}
		});
	}
	
	private void calcDiameter() {
		// Do we have everything we need?		
		if (!topoFreq.getValue().isEmpty() & !restFreq.getValue().isEmpty() & !sourceVelocity.getValue().isEmpty() & !redshift.getValue().isEmpty()) {
			if (frame.getValue().getValueAttribute().equals("Topocentric Frame")) {
				double d = 0.1 * diameter.getValue() * calcFWHM(Double.valueOf(topoFreq.getValue()) * 1e6);
				diameter_display.setValue(NumberFormat.getFormat("#.##").format(d));
			} else {
				//  TODO: Refactor to use Functions.velocity2Frequency()
				double rfreq = Double.valueOf(restFreq.getValue()) * 1e6;
				double tfreq = 0;
				if (doppler.getSelected().equals("Redshift")) {
					tfreq = rfreq / (1 + Double.valueOf(redshift.getValue()));
				} else if (doppler.getSelected().equals("Radio")) {
					tfreq = rfreq * (1 - (Double.valueOf(sourceVelocity.getValue()) * 1e3) / (c * 1e-2));
				} else {
					tfreq = rfreq / (1 + (Double.valueOf(sourceVelocity.getValue()) * 1e3) / (c * 1e-2));
				}
				double d = 0.1 * diameter.getValue() * calcFWHM(tfreq);
				diameter_display.setValue(NumberFormat.getFormat("#.##").format(d));
				topoFreq.setValue("" + tfreq * 1e-6);
			}
		}
	}
	
	private double calcFWHM(double freq) {
		double TeDB   = 13;
		double lambda = c / freq;
		int dish_radius = 50;
		return (1.02 + 0.0135 * TeDB) * 3437.7 * (lambda / (2 * dish_radius * 100));
	}
	
	public void validate() {

	}

	public void notify(String name, String value) {
		// handler for mode
		if (name.equals("mode")) {
			if (value.equals("Spectral Line")) {
				frame.show();
				topoFreq.hide();
				topoFreq.setAllowBlank(true);
				restFreq.show();
				restFreq.setAllowBlank(false);
				doppler.show();
				redshift.hide();
				redshift.setAllowBlank(true);
				sourceVelocity.show();
				sourceVelocity.setAllowBlank(false);
			} else {
				frame.hide();
				restFreq.hide();
				restFreq.setAllowBlank(true);
				doppler.hide();
				topoFreq.show();
				topoFreq.setAllowBlank(false);
				redshift.hide();
				redshift.setAllowBlank(true);
				sourceVelocity.hide();
				sourceVelocity.setAllowBlank(true);
			}

		} else if (name.equals("receiver")) {
			if (value.contains("(")) {
				float freq_low = Float.valueOf(value.substring(value.indexOf("(") + 1, value.indexOf("-") - 1)).floatValue();
				float freq_hi  = Float.valueOf(value.substring(value.indexOf("-") + 2, value.indexOf("G") - 1)).floatValue();
				float freq_mid = (freq_low + (freq_hi - freq_low) / 2) * 1000;
				restFreq.setValue("" + freq_mid);
				topoFreq.setValue("" + freq_mid);
			}
		}
		notifyAllForms();

	}
	
	private void updateDopplerFields() {
		if (doppler.getValue().getName("name") == "Redshift") {
			redshift.show();
			redshift.setAllowBlank(false);
			sourceVelocity.hide();
			sourceVelocity.setAllowBlank(true);
		} else {
			redshift.hide();
			redshift.setAllowBlank(true);
			sourceVelocity.show();
			sourceVelocity.setAllowBlank(false);
		}
	}

	class HandleFrame implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (frame.getValue().getValueAttribute().equals("Rest Frame")) {
				topoFreq.hide();
				topoFreq.setAllowBlank(true);
				restFreq.show();
				restFreq.setAllowBlank(false);
				doppler.show();
				updateDopplerFields();
			} else {
				topoFreq.show();
				topoFreq.setAllowBlank(false);
				restFreq.hide();
				restFreq.setAllowBlank(true);
				doppler.hide();
				sourceVelocity.hide();
				sourceVelocity.setAllowBlank(true);
				redshift.hide();
				redshift.setAllowBlank(true);
			}
			notifyAllForms();
		}
	}

	class HandleDoppler implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			updateDopplerFields();
			notifyAllForms();
		}
	}

	class HandleGalactic implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (galactic.getValue().getValueAttribute().equals("model")) {
				rightAscension.show();
				rightAscension.setAllowBlank(false);
				tBG.hide();
				tBG.setAllowBlank(true);
				tBG.setValue("0");
			} else if (galactic.getValue().getValueAttribute().equals("estimated")) {
				rightAscension.hide();
				rightAscension.setAllowBlank(true);
				tBG.show();
				tBG.setAllowBlank(false);
			} else {
				tBG.setValue("0");
				rightAscension.hide();
				rightAscension.setAllowBlank(true);
				tBG.hide();
				tBG.setAllowBlank(true);
			}
		}
	}
	
	public static ArrayList<String> getDoppler() {
		return new ArrayList<String>(Arrays.asList("Radio", "Optical",
				"Redshift"));
	}

}
