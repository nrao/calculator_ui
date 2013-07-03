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
import java.util.Arrays;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;

import edu.nrao.dss.client.forms.fields.GeneralRadioGroup;
import edu.nrao.dss.client.forms.fields.GeneralText;

public class GeneralForm extends BasicForm {
	private GeneralRadioGroup units, conversion;
	private GeneralText sensitivity, time;
	private double seconds;

	public GeneralForm() {
		super("General Information");
	}

	// Initial Layout
	public void initLayout() {
		//setHeading("General Information");
		
		conversion = new GeneralRadioGroup("conversion");
		conversion.setFieldLabel("Derive");
		conversion.setName("conversion");
		conversion.setId("conversion");
		conversion.setOrientation(Orientation.VERTICAL);
		
		Radio s2t  = new Radio();
		s2t.setBoxLabel("Observing Time from Desired Sensitivity");
		s2t.setValueAttribute("Sensitivity to Time");
		s2t.setToolTip("Do you want to calculate sensitivity from a desired observation duration?");
		s2t.setValue(true);
		conversion.add(s2t);
		Radio t2s = new Radio();
		t2s.setBoxLabel("Sensitivity from Observing Time");
		t2s.setToolTip("Do you want to calculate observation duration from a desired sensitivity?");
		t2s.setValueAttribute("Time to Sensitivity");
		conversion.add(t2s);
		

		// Desired sensitivity
		sensitivity = new GeneralText("sensitivity", "Desired Sensitivity");
		sensitivity.setMaxLength(6);
		
		// Desired time
		time = new GeneralText("time", "Total Time Required for On (+ Off) Observation (H:M:S or SS.SS)");
		time.setToolTip("Enter time in HH:MM:SS.SS, MM:SS.SS, or SS.SS");
		//time.setMaxLength(6);
		time.setAllowBlank(true);
		time.setValue("1");
		time.hide();
		time.setValidator(new Validator() {

			@Override
			public String validate(Field<?> field, String value) {
				seconds = 0.0;
				String[] values = value.split(":");
				try {
					if (values.length == 3) {
						seconds = Double.valueOf(values[0]) * 3600 + Double.valueOf(values[1]) * 60 + Double.valueOf(values[2]);
					} else if (values.length == 2) {
						seconds = Double.valueOf(values[0]) * 60 + Double.valueOf(values[1]);
					} else {
						seconds = Double.valueOf(value);
					}
				} catch (Exception e) {
					return "Enter time in HH:MM:SS.SS, MM:SS.SS, or SS.SS";
				}
				
				return null;
			}
			
		});
		
		// Unit choices
		units = new GeneralRadioGroup("units");
		units.setFieldLabel("Sensitivity Units");
		units.setOrientation(Orientation.VERTICAL);
		units.setName("units");
		units.setId("units");
		
		Radio choice = new Radio();
		choice.setBoxLabel("Flux Density (mJy)");
		choice.setName("flux");
		choice.setValueAttribute("flux");
		choice.setToolTip("Use flux density (mJy) for sensitivity units.");
		choice.setValue(true); // default
		units.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Antenna Temp., Ta (mK)");
		choice.setName("ta");
		choice.setValueAttribute("ta");
		choice.setToolTip("Use Ta (mK, and as measured below the Earth's atmosphere) for sensitivity units.");
		units.add(choice);

		choice = new Radio();
		choice.setBoxLabel("Main Beam Temp., Tmb (mK)");
		choice.setName("tm");
		choice.setValueAttribute("tm");
		choice.setToolTip("Use Tmb (mK, and as measured for the main beam) for sensitivity units.");
		units.add(choice);
			
		choice = new Radio();
		choice.setBoxLabel("Radiation Temp., Tr (mK)");
		choice.setName("tr");
		choice.setValueAttribute("tr");
		choice.setToolTip("Use Tr (mK, and as measured above the Earth's atmosphere) for sensitivity units.");
		units.add(choice);

		// attach listeners
		conversion.addListener(Events.Change, new handleConversion());
		units.addListener(Events.Change, new handleUnits());
		
		FormData fd = new FormData(50, 20);
		
		// attaching fields
		add(conversion);
		add(units);
		add(sensitivity, fd);
		add(time, new FormData(70, 20));

	}

	class handleConversion implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			
			if (conversion.getValue().getValueAttribute().equals("Time to Sensitivity")) {
				sensitivity.hide();
				sensitivity.setAllowBlank(true);
				sensitivity.setValue("1");
				time.show();
				time.setAllowBlank(false);
			} else {
				time.hide();
				time.setAllowBlank(true);
				time.setValue("1");
				sensitivity.show();
				sensitivity.setAllowBlank(false);
			}
		}
	}

	class handleUnits implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			notifyAllForms();
		}		
	}
	
	
	public void submit() {
		String t = time.getValue();
		time.setValue("" + seconds);
		super.submit();
		time.setValue(t);
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