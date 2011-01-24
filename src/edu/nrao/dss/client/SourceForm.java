package edu.nrao.dss.client;

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
	private String validatedSourceDec;
	private LabelField diameter_display;
	
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
		galactic.setFieldLabel("Source Contribution Corrections");
		galactic.setName("galactic");
		galactic.setId("galactic");
		galactic.setOrientation(Orientation.VERTICAL);
		
		choice = new Radio();
		choice.setBoxLabel("No Correction");
		//choice.setBoxLabel("Assume there is no contribution from the source to Tsys");
		//choice.setToolTip("No correction for the sources contribution.");
		choice.setToolTip("Assume there is no contribution from the source to Tsys.");
		choice.setValueAttribute("no_correction");
		choice.setName("no_correction");
		choice.setLabelSeparator("");
		choice.setValue(true);
		galactic.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Estimated");
		//choice.setBoxLabel("Enter a value for the expected contribution from the source to Tsys");
		//choice.setToolTip("Enter your own estimate for the contribution from Galactic Continuum.");
		choice.setToolTip("Enter a value for the expected contribution from the source to Tsys.");
		choice.setValueAttribute("estimated");
		choice.setName("estimated");
		choice.setLabelSeparator("");
		galactic.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Modeled");
		//choice.setBoxLabel("Use a model of the galactic contribution to Tsys");
		//choice.setToolTip("Use a standard model for the contribution from Galactic Continuum.");
		choice.setToolTip("Use a model of the galactic contribution to Tsys.");
		choice.setValueAttribute("model");
		choice.setName("model");
		choice.setLabelSeparator("");
		galactic.add(choice);
		
		diameter_display = new LabelField("0.0");
		//diameter_display.setFieldLabel("Source Diameter (arc minutes)");
		//diameter_display.setLabelSeparator(":");
		
		diameter = new Slider();
		diameter.setMinValue(0);
		diameter.setMaxValue(12);
		diameter.setValue(0);
		diameter.setIncrement(1);
		//diameter.setUseTip(false);
		diameter.setMessage("{0}");
		
		final SliderField sf = new SliderField(diameter);
		sf.setFieldLabel("Source Diameter (arc minutes)");
		//sf.setLabelSeparator("");
		sf.setName("source_diameter");
		sf.setId("source_diameter");
		
		rightAscension = new GeneralText("right_ascension", "Approx Right Ascension (HH:MM)");
		tBG            = new GeneralText("estimated_continuum", "Contribution (K)");
		tBG.setToolTip("Enter contribution of source to continuum level in units of K.");
		tBG.setValue("0");
        
		//sourceDec.setRegex("[1-9][0-9]{2}\\.[0-9]{2}","Invalid format");
		//sourceDec.setRegex("([0-9\\,\\.\\+\\-]+)","Invalid format: Must be a number.");
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
				}
				validatedSourceDec = "" + dec;
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

		diameter.addListener(Events.Change, new Listener<SliderEvent> () {

			@Override
			public void handleEvent(SliderEvent se) {
				// Do we have everything we need?		
				double c    = 3e10; // speed of light in cm/s
				double TeDB = 13;
				if (!topoFreq.getValue().isEmpty() & !restFreq.getValue().isEmpty() & !sourceVelocity.getValue().isEmpty() & !redshift.getValue().isEmpty()) {
					if (frame.getRawValue().equals("Topocentric Frame")) {
						double lambda = c / (Double.valueOf(topoFreq.getValue()) * 1e6);
						double fwhm   = (1.02 + 0.0135 * TeDB) * 3437.7 * (lambda / 20000);
						double d      = 0.1 * diameter.getValue() * fwhm;
						diameter_display.setValue(NumberFormat.getFormat("#.##").format(d));
					}
				}
			}
		});
		frame.addListener(Events.Change, new HandleFrame());
		doppler.addListener(Events.Select, new HandleDoppler());
		galactic.addListener(Events.Change, new HandleGalactic());

		FormData fd = new FormData(60, 20);
		 
		// attaching fields
		add(frame);
		add(restFreq, fd);
		add(topoFreq, fd);
		add(doppler);
		add(redshift, fd);
		add(sourceVelocity, fd);
		add(sf);
		add(diameter_display);
		//add(diameter);
		
		FieldSet fs = new FieldSet();
		fs.setHeading("Source Contribution Corrections");
		FormLayout layout = new FormLayout();  
		layout.setLabelWidth(200);  
		fs.setLayout(layout);
		
		fs.add(galactic);
		fs.add(rightAscension, fd);
		fs.add(tBG, fd);
		add(fs);
		
		//add(galactic);
		//add(rightAscension, fd);
		//add(tBG, fd);
		add(sourceDec, fd);
		
	}
	
	public void validate() {

	}
	
	public void submit() {
		// These aren't the droids your looking for.
		String orgValue = sourceDec.getValue();
		sourceDec.setValue(validatedSourceDec);
		super.submit();
		sourceDec.setValue(orgValue);
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
