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

package edu.nrao.dss.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import edu.nrao.dss.client.data.DataGrid;
import edu.nrao.dss.client.data.InputData;
import edu.nrao.dss.client.data.ResultsData;
import edu.nrao.dss.client.forms.DataForm;
import edu.nrao.dss.client.forms.GeneralForm;
import edu.nrao.dss.client.forms.HardwareForm;
import edu.nrao.dss.client.forms.SourceForm;

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
		TableLayout tableLayout = new TableLayout(2);
		tableLayout.setCellVerticalAlign(VerticalAlignment.TOP);
		rcp.setLayout(tableLayout);
		
		TableData tdLeft = new TableData();
		tdLeft.setVerticalAlign(VerticalAlignment.TOP);
		tdLeft.setWidth("550");
		
		TableData tdRight = new TableData();
		tdRight.setVerticalAlign(VerticalAlignment.TOP);
		tdRight.setWidth("550");
		
		final GeneralForm generalForm   = new GeneralForm();
		final HardwareForm hardwareForm = new HardwareForm();
		final SourceForm sourceForm     = new SourceForm();
		final DataForm dataForm         = new DataForm();
		
		generalForm.addListener(Events.Submit, new Listener<FormEvent>() {

			@Override
			public void handleEvent(FormEvent be) {
				if (hardwareForm.isValid()) {
					hardwareForm.submit();
				}
			}
			
		});
		hardwareForm.addListener(Events.Submit, new Listener<FormEvent>() {

			@Override
			public void handleEvent(FormEvent be) {
				if (sourceForm.isValid()) {
					sourceForm.submit();
				}
			}
			
		});
		sourceForm.addListener(Events.Submit, new Listener<FormEvent>() {

			@Override
			public void handleEvent(FormEvent be) {
				if (dataForm.isValid()) {
					dataForm.submit();
				}
			}
			
		});
		dataForm.addListener(Events.Submit, new Listener<FormEvent>() {

			@Override
			public void handleEvent(FormEvent be) {
				ResultsData.loadResults();
				InputData.loadInput();
			}
			
		});
		
		hardwareForm.addObserver(sourceForm);
		hardwareForm.addObserver(dataForm);
		sourceForm.addObserver(dataForm);
		
		ContentPanel questions = new ContentPanel();
		questions.setHeaderVisible(false);
		questions.setHeading("Questions");
		questions.setLayout(new RowLayout());
		questions.setScrollMode(Scroll.AUTOY);
		questions.setHeight(860);
		//questions.setAutoHeight(true);
		questions.setBorders(true);
		//questions.setAutoWidth(true);
		questions.setWidth(550);
		
		questions.add(generalForm);
		questions.add(hardwareForm);
		questions.add(sourceForm);
		questions.add(dataForm);
		
		rcp.add(questions, tdLeft);
		
		ContentPanel right = new ContentPanel();
		//right.setHeaderVisible(false);
		right.setHeading("Controls");
		right.setLayout(new TableLayout());
		
		ContentPanel controls = new ContentPanel();
		controls.setLayout(new RowLayout(Orientation.HORIZONTAL));
		controls.setHeaderVisible(false);
		controls.setBorders(false);
		controls.setSize(200, 30);
		
		Button update = new Button("Update Results");
		update.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if (generalForm.isValid()) {
				    generalForm.submit();
				}
			}
			
		});
		
		Button save = new Button("Save to File");
		save.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				Window.Location.assign("/calculator/results/text");
			}
			
		});
		
		TableData td = new TableData();
		td.setHorizontalAlign(HorizontalAlignment.CENTER);
		td.setMargin(20);
		td.setPadding(10);
		controls.add(update, new RowData(-1, -1, new Margins(0, 4, 0, 4)));
		controls.add(save, new RowData(-1, -1, new Margins(0, 4, 0, 4)));
		right.add(controls, td);
		
		TabPanel results = new TabPanel();
		results.setLayoutData(new RowLayout());
		results.setHeight(800);
		//results.setAutoHeight(true);
		//results.setAutoWidth(true);
		results.setWidth(600);
		
		TabItem resultsFormatted = new TabItem("Results");  
		resultsFormatted.setScrollMode(Scroll.AUTO);  
		resultsFormatted.addStyleName("pad-text");
		resultsFormatted.setAutoLoad(ResultsData.getResultsRB());
		results.add(resultsFormatted);
		
		TabItem resultsGrid = new TabItem("Result Grids");
		resultsGrid.setLayout(new RowLayout());
		resultsGrid.setScrollMode(Scroll.AUTO);
		
		resultsGrid.add(ResultsData.getResultsGrid());
		resultsGrid.add(InputData.getInputGrid());
		results.add(resultsGrid);
		
		right.add(results);
		rcp.add(right);
		
		RootPanel rp = RootPanel.get();
		rp.add(new Html("<a href=\"https://help.nrao.edu\">Help Desk</a> | <a href=\"/docs/Calculator_ug.pdf\">Users Guide</a>"));
		rp.add(rcp);
	}
}
