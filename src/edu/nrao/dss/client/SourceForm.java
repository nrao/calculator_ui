package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.Arrays;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Slider;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.core.client.GWT;
public class SourceForm extends BasicForm {
	private GeneralText sourceDec, minElev, restFreq, sourceVelocity, redshift,
			topoFreq, rightAscension, tBG;
	private GeneralCombo frame, doppler, size;
	private CheckBox galactic;

	public SourceForm() {
		super("Source Information");
	}

	public void initLayout() {
		setCollapsible(true);
		sourceDec = new GeneralText("sourceDec", "Source Declination");
		minElev = new GeneralText("minElev", "Minimum Elevation");
		frame = new GeneralCombo("frame", "Frames", getFrames());
		restFreq = new GeneralText("restFreq", "Rest Frequency");
		topoFreq = new GeneralText("topoFreq", "Topocentric Frequency");
		doppler = new GeneralCombo("doppler", "Doppler Correction",
				getDoppler());
		sourceVelocity = new GeneralText("sourceVelocity", "Source Velocity");
		redshift = new GeneralText("redshift", "Redshift");
		size = new GeneralCombo("size", "Source Size", new ArrayList<String>(
				Arrays.asList("Point source", "Diameter < 2 FWHM",
						"Diameter > 2 FWHM")));
		galactic = new CheckBox();
		galactic.setBoxLabel("Add contribution from Galactic Continuum");
		galactic.setLabelSeparator("");
		rightAscension = new GeneralText("rightAscention",
				"Enter right ascension");
		tBG = new GeneralText("tBG",
				"Enter contribution of source to continuum level");
        
		//sourceDec.setRegex("[1-9][0-9]{2}\\.[0-9]{2}","Invalid format");
		sourceDec.setRegex("\\d\\d\\.\\d\\d","Invalid format");
		sourceDec.setMessageTarget("side");
		//sourceDec.setRegexText("sdf");
		//sourceDec.setAllowBlank(false);
		///sourceDec.forceInvalid("ahhahl");
		//FieldMessages me = new FieldMessages();
		//me.setInvalidText("wrong!!");
		//sourceDec.setMessages(me);
		//FieldMessages me =sourceDec.new FieldMessages();
		//me.setInvalidText("AHAH");
		//sourceDec.setMessages(me);
		// initial state of questions
		//Validator jk = sourceDec.getValidator();
		
		restFreq.hide();
		doppler.hide();
		redshift.hide();
		sourceVelocity.hide();
		rightAscension.hide();

		frame.addListener(Events.Select, new HandleFrame());
		doppler.addListener(Events.Select, new HandleDoppler());
		galactic.addListener(Events.Change, new HandleGalactic());

		// attaching fields
		add(sourceDec);
		add(minElev);
		add(frame);
		add(restFreq);
		add(topoFreq);
		add(redshift);
		add(doppler);
		add(redshift);
		add(sourceVelocity);
		// add(new Label("Source Size:"));
		// add(getSlider());
		add(size);
		add(galactic);
		add(rightAscension);
		add(tBG);

	}

	public void validate() {

	}

	// TODO
	// Modify this to fit into the design
	// Sliders arn't Fields
	// Work around: maybe create a hidden field that observes
	// the value of the slider
	public Slider getSlider() {
		Slider slider = new Slider();
		slider.setBounds(10, 10, 200, Style.DEFAULT);
		slider.setMinValue(1);
		slider.setMaxValue(100);
		slider.setIncrement(1);
		slider.setValue(100);
		slider.setMessage("Scale by {0}%");
		return slider;
	}

	public void notify(String name, String value) {
		// handler for mode
		if (name.equals("mode")) {
			if (value.equals("Spectral Line")) {
				frame.show();
				restFreq.hide();
				doppler.hide();
				topoFreq.show();
				redshift.hide();
				sourceVelocity.hide();
			} else {
				frame.hide();
				restFreq.hide();
				doppler.hide();
				topoFreq.show();
				redshift.hide();
				sourceVelocity.hide();
			}

		}

	}

	class HandleFrame implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (frame.getValue().getName("name") == "Rest Frame") {
				topoFreq.hide();
				restFreq.show();
				doppler.show();
				sourceVelocity.show();
				redshift.hide();
			} else {
				topoFreq.show();
				restFreq.hide();
				doppler.hide();
				sourceVelocity.hide();
				redshift.hide();
			}

		}
	}

	class HandleDoppler implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (doppler.getValue().getName("name") == "RedShift") {
				redshift.show();
				sourceVelocity.hide();
			} else {
				redshift.hide();
				sourceVelocity.show();
			}
		}
	}

	class HandleGalactic implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (galactic.getValue()) {
				rightAscension.show();
			} else {
				rightAscension.hide();
			}
		}
	}

	public static ArrayList<String> getFrames() {
		return new ArrayList<String>(Arrays.asList("Topocentric Frame",
				"Rest Frame"));
	}

	public static ArrayList<String> getDoppler() {
		return new ArrayList<String>(Arrays.asList("Radio", "Doppler",
				"RedShift"));
	}

}
