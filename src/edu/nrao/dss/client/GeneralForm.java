package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.Arrays;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.google.gwt.core.client.GWT;

public class GeneralForm extends BasicForm {
	private RadioGroup units, conversion, semester;
	private GeneralText sensitivity, time;

	public GeneralForm() {
		super("General Information");
		//initLayout();
	}

	// Initial Layout
	public void initLayout() {
		//setHeading("General Information");
		setCollapsible(true);
		setLabelAlign(LabelAlign.RIGHT);
		setLabelWidth(250);
		
		conversion = new RadioGroup("conversion");
		conversion.setFieldLabel("Conversion");
		conversion.setName("conversion");
		conversion.setId("conversion");
		conversion.setOrientation(Orientation.VERTICAL);
		
		Radio s2t  = new Radio();
		s2t.setBoxLabel("Sensitivity to Time");
		s2t.setValueAttribute("Sensitivity to Time");
		s2t.setToolTip("Do you want to calculate sensitivity from a desired observation duration?");
		s2t.setValue(true);
		conversion.add(s2t);
		Radio t2s = new Radio();
		t2s.setBoxLabel("Time to Sensitivity");
		t2s.setToolTip("Do you want to calculate observation duration from a desired sensitivity?");
		t2s.setValueAttribute("Time to Sensitivity");
		conversion.add(t2s);
		

		// Desired sensitivity
		sensitivity = new GeneralText("sensitivity", "Enter Desired Sensitivity");
		
		// Desired time
		time = new GeneralText("time", "Enter Desired Time");
		time.setAllowBlank(true);
		time.hide();
		
		// Unit choices
		units = new RadioGroup("units");
		units.setFieldLabel("Sensitivity Units");
		units.setOrientation(Orientation.VERTICAL);
		units.setName("units");
		units.setId("units");
		
		Radio choice = new Radio();
		choice.setBoxLabel("flux density (mJy)");
		choice.setName("flux");
		choice.setValueAttribute("flux");
		choice.setToolTip("Use flux density (mJy) for sensitivity units.");
		
		units.add(choice);
		choice = new Radio();
		choice.setBoxLabel("Ta (mK)");
		choice.setName("ta");
		choice.setValueAttribute("ta");
		choice.setToolTip("Use Ta (mK, and as measured below the Earth's atmosphere) for sensitivity units.");
		units.add(choice);

		choice = new Radio();
		choice.setBoxLabel("Tr (mK)");
		choice.setName("tr");
		choice.setValueAttribute("tr");
		choice.setToolTip("Use Tr (mK, and as measured above the Earth's atmosphere) for sensitivity units.");
		choice.setValue(true); // default
		units.add(choice);

		semester = new RadioGroup("semester");
		semester.setFieldLabel("Semester");
		semester.setName("semester");
		semester.setId("semester");
		
		choice = new Radio();
		choice.setBoxLabel("A");
		choice.setValueAttribute("A");
		choice.setName("A");
		choice.setValue(true);
		semester.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("B");
		choice.setValueAttribute("B");
		choice.setName("B");
		semester.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("All");
		choice.setValueAttribute("All");
		choice.setName("All");
		semester.add(choice);

		// attach listeners
		conversion.addListener(Events.Change, new handleConversion());

		// attaching fields
		add(conversion);
		add(units);
		add(sensitivity);
		add(time);
		add(semester);

	}

	class handleConversion implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (conversion.getValue().getValueAttribute().equals("Time to Sensitivity")) {
				sensitivity.hide();
				sensitivity.setAllowBlank(true);
				time.show();
				time.setAllowBlank(false);
			} else {
				time.hide();
				time.setAllowBlank(true);
				sensitivity.show();
				sensitivity.setAllowBlank(false);
			}
		}
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(String name, String value) {
		// TODO Auto-generated method stub
		
	}

}