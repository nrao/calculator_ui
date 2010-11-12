package edu.nrao.dss.client;

import com.google.gwt.core.client.EntryPoint;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Calculator_ui implements EntryPoint {
	public TabPanel tabPanel;

	public void onModuleLoad() {
		initLayout();
	}
	
	private void initLayout(){
		
		// Root Content Panel
		ContentPanel rcp = new ContentPanel();
		rcp.setFrame(true);
		rcp.setHeading("Sensitivity Calculator");
		rcp.setLayout(new TableLayout(2));
		
		TableData tdLeft = new TableData();
		tdLeft.setVerticalAlign(VerticalAlignment.TOP);
		tdLeft.setWidth("550");
		
		TableData tdRight = new TableData();
		tdRight.setVerticalAlign(VerticalAlignment.TOP);
		tdRight.setWidth("550");
		
		GeneralForm generalForm   = new GeneralForm();
		HardwareForm hardwareForm = new HardwareForm();
		SourceForm sourceForm     = new SourceForm();
		DataForm dataForm         = new DataForm();

		hardwareForm.addObserver(sourceForm);
		hardwareForm.addObserver(dataForm);

		ContentPanel questions = new ContentPanel();
		questions.setHeading("Questions");
		questions.setLayout(new RowLayout());
		questions.setScrollMode(Scroll.AUTO);
		questions.setHeight(700);
		questions.setBorders(true);
		questions.add(generalForm);
		questions.add(hardwareForm);
		questions.add(sourceForm);
		questions.add(dataForm);
		
		rcp.add(questions, tdLeft);

//		ContentPanel right = new ContentPanel();
//		right.setHeaderVisible(false);
//		right.setHeight(700);
//		right.setFrame(true);
//		right.add(new ResultsPanel());
//		ControlsPanel controls = new ControlsPanel();
//		right.add(controls);
//		rcp.add(right, tdRight);
		rcp.add(new ResultsPanel(), tdRight);

		RootPanel.get().add(rcp);
	}
}
