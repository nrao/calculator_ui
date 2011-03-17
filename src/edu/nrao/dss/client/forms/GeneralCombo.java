package edu.nrao.dss.client.forms;

import java.util.ArrayList;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;


public class GeneralCombo extends ComboBox<ComboModel> {
	private ListStore<ComboModel> comboStore = new ListStore<ComboModel>();

	public GeneralCombo() {

	}

	public GeneralCombo(String name, String label,
			ListStore<ComboModel> newComboStore) {
		comboStore = newComboStore;
		init(name, label);

	}

	public GeneralCombo(String name, String label,
			ArrayList<String> newComboStore) {
		for (String key : newComboStore) {
			comboStore.add(new ComboModel(key));
		}
		init(name, label);
	}

	public GeneralCombo(String name, String label) {
		comboStore = new ListStore<ComboModel>();
		init(name, label);
	}

	public ListStore<ComboModel> ListToStore(ArrayList<String> newComboStore) {
		ListStore<ComboModel> result = new ListStore<ComboModel>();

		for (String key : newComboStore) {
			// addOption(key);
			result.add(new ComboModel(key));
		}
		return result;
	}

	public void init(String name, String label) {
		setFieldLabel(label);
		setName(name);
		setId(name);
		setData("name", name);
		setStore(comboStore);
		setDisplayField("name");
		setValueField("name");
		setTypeAhead(true);
		setTriggerAction(TriggerAction.ALL);
		setEditable(false);
		setValue(this.getStore().getAt(0));
		//setAutoWidth(true);
	}

	public void addOption(String value) {
		ComboModel option = new ComboModel(value);
		this.getStore().add(option);
	}

	public void removeOption(ComboModel option) {
		this.getStore().remove(option);
	}

	public void initiateComboStore(ArrayList<String> array) {
		this.setStore(ListToStore(array));
		this.setValue(this.getStore().getAt(0));
		if (this.getStore().getCount() == 1) {
			// this.setVisible(false);
			this.disable();

		} else {
			this.enable();
			// this.setVisible(true);
		}
		
	}

	public String getSelected() {
		if (this.getSelection().size() > 0) {
			return this.getSelection().get(0).get("name");
		} else {
			return "NONE";
		}
	}

	public void setComboStore(ArrayList<String> options) {
		String value = getRawValue();
		reset();

		this.getStore().removeAll();
		for (String key : options) {
			key = key.replace("\"", "");
			ComboModel option = new ComboModel(key);
			this.getStore().add(option);
		}
		ComboModel newSelection = this.getStore().findModel("name",
				value);
		
		// if the currently selected is in new store select it else select first
		if (newSelection == null) {
			this.setValue(this.getStore().getAt(0));
		} else {
			this.setValue(newSelection);
		}

		// if there is only one option available disable it
		if (this.getStore().getCount() == 1) {
			// this.setVisible(false);
			this.disable();
			
		} else {
			this.enable();
			// this.setVisible(true);
		}
		
		if (newSelection == null) {
			getInputEl().setStyleAttribute("background-color", "#C11000");
			
			Timer timer = new Timer() {
				public void run() {
					getInputEl().setStyleAttribute("background-color", "white");
				}
			};
			timer.schedule(2000);
		}
	}

}
