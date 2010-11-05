package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.List;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;

public class ResultsPanel extends ContentPanel {

	private Grid<Result> grid;
	
	public ResultsPanel() {
		initLayout();
		layout(true);
	}
	
	private void initLayout() {
		setBodyBorder(true);
		setHeaderVisible(false);
		setLayout(new FitLayout());
		setSize(900,450);//???
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		ColumnConfig column = new ColumnConfig();
		column.setId("term");
		column.setHeader("Variable");
		column.setWidth(200);
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("value");
		column.setHeader("Value");
		column.setWidth(200);
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("units");
		column.setHeader("Units");
		column.setWidth(100);
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("equation");
		column.setHeader("Equation ");
		column.setWidth(200);
		configs.add(column);

		ResultStore store = ResultStore.getResultStore();
		store.update();
		GWT.log("st = "+store.toString());	
		grid = new Grid<Result>(store, new ColumnModel(configs));
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoExpandColumn("equation");
		grid.setBorders(false);
		grid.setStripeRows(true);
		add(grid);
	}
}
