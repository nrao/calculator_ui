package edu.nrao.dss.client.forms.fields;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.user.client.Element;

public class GeneralCheckbox extends CheckBox {

	public GeneralCheckbox() {
		super();
		addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent be) {
				//getElement().getParentElement().addClassName("x-grid3-dirty-cell");
				Element el = getElement();
				if (el != null) {
					com.google.gwt.dom.client.Element parent = el.getParentElement();
					if (parent != null && isRendered()) {
						parent.addClassName("x-grid3-dirty-cell");
					}
				}
			}
			
		});
	}
}
