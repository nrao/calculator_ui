package edu.nrao.dss.client;

import java.util.ArrayList;
import java.util.Arrays;



import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.core.client.GWT;


public class DataForm extends BasicForm {
	private GeneralText nOnPerOff, rSigRef, bw, bwRef, nAverageRef;
	private GeneralCombo nOverlap, smoothingResolution;
	private CheckBox averagePol, differenceSignal,smoothing;

	public DataForm() {
		super("Data Reduction");
	}

	// Initial Layout
	public void initLayout() {
		setCollapsible(true);
		nOnPerOff = new GeneralText("nOnPerOff","Enter the number of on-source or on frequency per off observations");
		rSigRef = new GeneralText("rSigRef","Enter ratio of time spent on the signal observation to the reference observation");
		nOverlap = new GeneralCombo("nOverlap","Enter number of spectral windows centered",  new ArrayList<String>(Arrays.asList("1","2")));
		averagePol = new CheckBox();
		averagePol.setBoxLabel("Average orthognal polarizations");
		averagePol.setLabelSeparator("");
		differenceSignal =  new CheckBox();
		differenceSignal.setBoxLabel("Difference signal and reference observations");
		differenceSignal.setLabelSeparator("");
		smoothing =  new CheckBox();
		smoothing.setBoxLabel("Smooth on-source and off-source data");
		smoothing.setLabelSeparator("");
		smoothingResolution = new GeneralCombo("resolutionSmoothing", "Select resolution to smooth to", new ArrayList<String>(Arrays.asList("Veloctiy Resolution","Topocentric frame", "Rest Frame")));
		bw = new GeneralText("bw","Value used to smooth on-source data");
		bwRef = new GeneralText("bwRef","Value used to smooth off-source data");
		nAverageRef = new GeneralText("nAverageRef","Enter the number of references used for averaging");
		

		//initial state
		nOnPerOff.hide();
		rSigRef.hide();
		smoothingResolution.hide();
		bw.hide();
		bwRef.hide();
		
		smoothing.addListener(Events.Change, new HandleSmoothing());
		
		//attaching fields
		add(nOnPerOff);
		add(rSigRef);
		add(nOverlap);
		add(averagePol);
		add(differenceSignal);
		add(smoothing);
		add(smoothingResolution);
		add(bw);
		add(bwRef);
		add(nAverageRef);
		
		
		//attaching fields

	}


	class HandleSmoothing implements Listener<FieldEvent> {
		public void handleEvent(FieldEvent fe) {
			if (smoothing.getValue()) {
				smoothingResolution.show();
				bw.show();
				bwRef.show();
			} else {
				smoothingResolution.hide();
				bw.hide();
				bwRef.hide();
			}
		}
	}


	
	public void notify(String name, String value) {
		// handler for mode
		if (name.equals("switching")) {
			if (value.equals("Total Power")) {
				nOnPerOff.hide();
				rSigRef.hide();
			} else {
				nOnPerOff.show();
				rSigRef.show();
			}

		}else{
			
		}
		
		if (name.equals("mode")) {
			if (value.equals("Spectral Line")) {
				smoothing.show();
				if (smoothing.getValue()) {
					smoothingResolution.show();
					bw.show();
					bwRef.show();
				} else {
					smoothingResolution.hide();
					bw.hide();
					bwRef.hide();
				}
			} else {
				smoothing.hide();
				smoothingResolution.hide();
				bw.hide();
				bwRef.hide();
			}

		}else{
			
		}
		
		if (name.equals("backend")) {
			if (value.equals("Spectrometer")) {
				nOverlap.show();
			} else {
				nOverlap.hide();
			}

		}

	}
	
	public void validate() {

	}
	
	

}