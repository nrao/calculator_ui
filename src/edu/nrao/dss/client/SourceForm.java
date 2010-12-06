package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.SliderField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
public class SourceForm extends BasicForm {
	private GeneralText sourceDec, restFreq, sourceVelocity, redshift,
			topoFreq, rightAscension, tBG;
	private GeneralCombo doppler, size;
	private RadioGroup galactic, frame;
	private Slider diameter;
	
	public SourceForm() {
		super("Source Information");
	}

	public void initLayout() {
		setCollapsible(true);
		sourceDec = new GeneralText("declination", "Source Declination");
		sourceDec.setValue("38.43");
		restFreq  = new GeneralText("rest_freq", "Rest Frequency (MHz)");
		topoFreq  = new GeneralText("topocentric_freq", "Topocentric Frequency (MHz)");
		doppler   = new GeneralCombo("doppler", "Doppler Correction", getDoppler());
		doppler.setValue(new ComboModel("Optical"));
		sourceVelocity = new GeneralText("source_velocity", "Source Velocity (km/s)");
		sourceVelocity.setValue("0");
		redshift = new GeneralText("redshift", "Redshift");
		redshift.setValue("0");
		size     = new GeneralCombo("source_size", "Source Size", new ArrayList<String>(
				             Arrays.asList("Point source", "Diameter < 2 FWHM", "Diameter > 2 FWHM")));
		
		frame = new RadioGroup("frame");
		frame.setFieldLabel("Frame");
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
		galactic.setFieldLabel("Source Contribution Corrections");
		galactic.setName("galactic");
		galactic.setId("galactic");
		galactic.setOrientation(Orientation.VERTICAL);
		
		choice = new Radio();
		choice.setBoxLabel("No Correction");
		choice.setToolTip("No correction for the sources contribution.");
		choice.setValueAttribute("no_correction");
		choice.setName("no_correction");
		choice.setLabelSeparator("");
		choice.setValue(true);
		galactic.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Estimated");
		choice.setToolTip("Enter your own estimate for the contribution from Galactic Continuum.");
		choice.setValueAttribute("estimated");
		choice.setName("estimated");
		choice.setLabelSeparator("");
		galactic.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Modeled");
		choice.setToolTip("Use a standard model for the contribution from Galactic Continuum.");
		choice.setValueAttribute("model");
		choice.setName("model");
		choice.setLabelSeparator("");
		galactic.add(choice);
		
		diameter = new Slider();
		diameter.setMinValue(4);
		diameter.setMaxValue(20);
		diameter.setValue(4);
		diameter.setIncrement(1);
		diameter.setMessage("{0} 10^-1 arc minutes");
		
		final SliderField sf = new SliderField(diameter);
		sf.setFieldLabel("Angular Diameter");
		sf.setName("angular_diameter");
		sf.setId("angular_diameter");
		
		rightAscension = new GeneralText("right_ascension",
				"Enter right ascension");
		tBG = new GeneralText("estimated_continuum",
				"Enter contribution of source to continuum level");
		tBG.setValue("0");
        
		//sourceDec.setRegex("[1-9][0-9]{2}\\.[0-9]{2}","Invalid format");
		sourceDec.setRegex("([0-9\\,\\.\\+\\-]+)","Invalid format: Must be a number.");
		sourceDec.setMessageTarget("side");
		sourceDec.setValidator(new Validator () {

			@Override
			public String validate(Field<?> field, String value) {
				if (value.isEmpty() || value.equals("-")) {
					return null;
				}
				
				float min_el = (float) 0.0;
				float dec = (Float.valueOf(value).floatValue() + min_el);
				if (dec <= -51.57) {
					return "Declination does not achieve the desired minimum elevation for the chosen receiver and frequency.";
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

		frame.addListener(Events.Change, new HandleFrame());
		doppler.addListener(Events.Select, new HandleDoppler());
		galactic.addListener(Events.Change, new HandleGalactic());

		// attaching fields
		add(sourceDec);
		add(frame);
		add(restFreq);
		add(topoFreq);
		add(doppler);
		add(redshift);
		add(sourceVelocity);
		add(size);
		add(sf);
		add(galactic);
		add(rightAscension);
		add(tBG);

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
				float freq_hi  = Float.valueOf(value.substring(value.indexOf("-") + 2, value.indexOf(")"))).floatValue();
				float freq_mid = (freq_low + (freq_hi - freq_low) / 2) * 1000;
				restFreq.setValue("" + freq_mid);
				topoFreq.setValue("" + freq_mid);
			}
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
				sourceVelocity.show();
				sourceVelocity.setAllowBlank(false);
				redshift.hide();
				redshift.setAllowBlank(true);
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

		}
	}

	class HandleDoppler implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (doppler.getValue().getName("name") == "RedShift") {
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
	}

	class HandleGalactic implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (galactic.getValue().getValueAttribute().equals("model")) {
				rightAscension.show();
				rightAscension.setAllowBlank(false);
				tBG.hide();
				tBG.setAllowBlank(true);
			} else if (galactic.getValue().getValueAttribute().equals("estimated")) {
				rightAscension.hide();
				rightAscension.setAllowBlank(true);
				tBG.show();
				tBG.setAllowBlank(false);
			} else {
				rightAscension.hide();
				rightAscension.setAllowBlank(true);
				tBG.hide();
				tBG.setAllowBlank(true);
			}
		}
	}
	
	public static ArrayList<String> getDoppler() {
		return new ArrayList<String>(Arrays.asList("Radio", "Optical",
				"RedShift"));
	}

}
