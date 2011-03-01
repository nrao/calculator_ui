package edu.nrao.dss.client.data;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.HttpProxy;
import com.extjs.gxt.ui.client.data.JsonReader;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.http.client.RequestBuilder;


public class DataGrid extends ContentPanel {
	
	private BaseListLoader<BaseListLoadResult<BaseModelData>> loader;
	private String heading;
	
	public DataGrid(String heading, DataType datatype) {
		this.heading = heading;
		initLayout(datatype);
	}
	
	private void initLayout(DataType datatype) {
		setHeading(heading);
		setBodyBorder(true);
		setLayout(new FitLayout());
		setAutoHeight(true);
		setScrollMode(Scroll.AUTOY);
		setCollapsible(true);
		
		String rootUrl = "/calculator/get_results";
				
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, rootUrl);

		JsonReader<BaseListLoadResult<BaseModelData>> reader = new JsonReader<BaseListLoadResult<BaseModelData>>(datatype);
		HttpProxy<BaseListLoadResult<BaseModelData>> proxy   = new HttpProxy<BaseListLoadResult<BaseModelData>>(builder);
		loader = new BaseListLoader<BaseListLoadResult<BaseModelData>>(proxy, reader);  
		ListStore<BaseModelData> store = new ListStore<BaseModelData>(loader);
		
		ColumnModel cm           = initColumnModel();
	    final Grid<BaseModelData> grid = new Grid<BaseModelData>(store, cm);
	    grid.setAutoHeight(true);
	    
		add(grid);
		grid.setBorders(true);
	}
	
	private ColumnModel initColumnModel() {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

//	    ColumnConfig column = new ColumnConfig("term", "Variable", 200);
//	    configs.add(column);

	    String termHeading;
	    if (heading.equals("Results")) {
	    	termHeading = "Term";
	    } else {
	    	termHeading = "Field";
	    }
	    
	    ColumnConfig column = new ColumnConfig("label", termHeading, 225);
	    configs.add(column);
	    
	    column = new ColumnConfig("value", "Value", 200);
	    configs.add(column);
	    
//	    column = new ColumnConfig("display", "Value", 300);
//	    configs.add(column);
	    
//	    column = new ColumnConfig("units", "Units", 100);
//	    configs.add(column);
	    
//	    column = new ColumnConfig("equation", "Equation", 100);
//	    configs.add(column);
	    
	    return new ColumnModel(configs);
	}
	
	public void load() {
		loader.load();
	}
	
}
