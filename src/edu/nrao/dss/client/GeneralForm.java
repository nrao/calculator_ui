package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.Arrays;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.google.gwt.core.client.GWT;

public class GeneralForm extends BasicForm {
	private RadioGroup units;
	private GeneralCombo conversion, trimester;
	private GeneralText sensitivity, time;

	public GeneralForm() {
		super("General Information");
	}

	// Initial Layout
	public void initLayout() {
		conversion = new GeneralCombo("conversion", "Conversion",
				new ArrayList<String>(Arrays.asList("Sensitivity to Time", "Time to Sensitivity")));

		// Desired sensitivity
		sensitivity = new GeneralText("sensitivity",
				"Enter Desired sensitivity");
		
		// Desired time
		time = new GeneralText("time", "Enter desired time");

		
		// Unit choices
		units = new RadioGroup("units");
		Radio choice = new Radio();
		choice = new Radio();
		choice.setBoxLabel("flux density (mJy)");
		choice.setValue(true);
		choice.setName("flux");
		choice.setValueAttribute("flux");
		units.add(choice);
		choice = new Radio();
		choice.setBoxLabel("Tant (mK)");
		choice.setName("tant");
		choice.setValueAttribute("tant");
		units.add(choice);

		choice = new Radio();
		choice.setBoxLabel("Trad (mK)");
		choice.setName("trad");
		choice.setValueAttribute("trad");
		units.add(choice);

		units.setFieldLabel("Sensitivity units");
		units.setOrientation(Orientation.VERTICAL);
		units.setName("units");
		units.setId("units");
		
		trimester = new GeneralCombo("trimester", "Trimester",
				new ArrayList<String>(Arrays.asList("A", "B", "C", "All")));

		// attach listeners
		conversion.addListener(Events.Select, new handleConversion());

		// intial state of questions
		time.hide();

		// attaching fields
		this.add(conversion);
		this.add(sensitivity);
		this.add(time);
		this.add(sensitivity);
		this.add(units);
		this.add(trimester);

	}

	class handleConversion implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			GWT.log("CHANGED to " + conversion.getValue());
			if (conversion.getValue().getName("name") == "Time to Sensitivity") {
				sensitivity.hide();
				time.show();
			} else {
				time.hide();
				sensitivity.show();
			}
		}
	}

	public void notify(String name, String value) {

	}

	public void validate() {

	}

}