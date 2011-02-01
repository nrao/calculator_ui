package edu.nrao.dss.client;

import java.util.ArrayList;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.google.gwt.core.client.GWT;

public abstract class BasicForm extends FormPanel {
	private Button update;
	public ArrayList<BasicForm> observers = new ArrayList<BasicForm>();
	FormButtonBinding saveBinding = new FormButtonBinding(this);

	public BasicForm(String title) {
		setFrame(true);
		setCollapsible(true);
		setAction("/calculator/set_terms/");
		setMethod(FormPanel.Method.POST);

		// setting layout
		setLabelWidth(200);
		setLabelAlign(LabelAlign.RIGHT);

		setHeading(title);
		initLayout();

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.setTableWidth("100%");
		buttons.setSpacing(10);
		
		
		update = new Button("Update Results");
		
		update.addSelectionListener(updateTerms);
		saveBinding.addButton(update);
		
		TableData td = new TableData();
		td.setHorizontalAlign(HorizontalAlignment.CENTER);
		buttons.add(update, td);
		
		add(buttons);

		layout(true);
	}

	public abstract void validate();

	public abstract void initLayout();

	public abstract void notify(String name, String value);

	public void addObserver(BasicForm form) {
		observers.add(form);
	}

	public void notifyAllForms() {

		for (BasicForm form : observers) {
			for (Field fe : this.getFields()) {
				form.notify(fe.getName(), fe.getRawValue());
			}
		}
	}
	
	SelectionListener<ButtonEvent> updateTerms = new SelectionListener<ButtonEvent>() {
		public void componentSelected(ButtonEvent ce) {
			submit();
			ResultsData.loadResults();
		}
	};

}
