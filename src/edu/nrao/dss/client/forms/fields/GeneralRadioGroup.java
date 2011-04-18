package edu.nrao.dss.client.forms.fields;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.google.gwt.user.client.Element;

public class GeneralRadioGroup extends RadioGroup {

	public GeneralRadioGroup(String name) {
		super(name);
		
		addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent be) {
				getElement().getParentElement().addClassName("x-grid3-dirty-cell");
				
			}
			
		});
		
	}
}
