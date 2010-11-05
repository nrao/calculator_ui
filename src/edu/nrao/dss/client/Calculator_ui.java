package edu.nrao.dss.client;

import com.google.gwt.core.client.EntryPoint;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calculator_ui implements EntryPoint {
	public TabPanel tabPanel;

	public void onModuleLoad() {
		// TODO: figure out how size content without static sizes

		// Root Content Panel
		ContentPanel rcp = new ContentPanel();
		rcp.setFrame(true);
		rcp.setHeading("Sensitivity Calculator");
		rcp.setLayout(new ColumnLayout());

		// tabPanel
		tabPanel = new TabPanel();
		GeneralForm generalForm = new GeneralForm();
		HardwareForm hardwareForm = new HardwareForm();
		SourceForm sourceForm = new SourceForm();
		DataForm dataForm = new DataForm();

		hardwareForm.addObserver(sourceForm);
		hardwareForm.addObserver(dataForm);

		addTab(generalForm, "General",
				"Specify your general observation settings.");
		addTab(hardwareForm, "Hardware",
				"Specify your hardware configuration settings.");
		addTab(sourceForm, "Source", "Specify your source information.");
		addTab(dataForm, "Data Reduction",
				"Specify your data reduction strategey.");

		rcp.add(tabPanel, new ColumnData(.62));

		LayoutContainer gridArea = new LayoutContainer();
		gridArea.setLayout(new AccordionLayout());

		// Data Store Panel
		ContentPanel ds = new ContentPanel();
		ds.setCollapsible(true);
		ds.setHeading("Results");
		ds.setHeight(500);// WHY Me?
		ds.setFrame(true);
		ds.add(new ResultsPanel());
		gridArea.add(ds);

		// Configuration panel
		VerticalPanel config = new ConfigPanel();
		config.setTableWidth("35%");
		rcp.add(config);
		rcp.layout();

		RootPanel.get().add(rcp);
		RootPanel.get().add(gridArea);
		// hardwareForm.initiate();
	}

	private void addTab(ContentPanel container, String title, String toolTip) {
		TabItem item = new TabItem(title);
		item.setId(title);
		item.getHeader().setToolTip(toolTip);
		item.setLayout(new FitLayout());
		item.add(container);
		item.setSize(600, 600); // TODO: It would be nice not to have to do
								// this.
		tabPanel.add(item);
	}

}
