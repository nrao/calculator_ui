package edu.nrao.dss.client.forms;

import java.util.ArrayList;
import java.util.Arrays;



import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;


public class DataForm extends BasicForm {
	private GeneralText nOnPerOff, rSigRef, nAverageRef, resolution;
	//private GeneralCombo nOverlap;
	private CheckBox averagePol, differenceSignal;
	private RadioGroup smoothing, smoothing_factor;
	private Label smoothing_factor_inst;
	private FieldSet smoothingFieldSet;

	public DataForm() {
		super("Data Reduction");
	}

	// Initial Layout
	public void initLayout() {
		setCollapsible(true);
		nOnPerOff = new GeneralText("nOnPerOff","Number of Ons per Offs");
		nOnPerOff.setToolTip("Enter the number of on-source or on frequency per off observations");
		rSigRef = new GeneralText("rSigRef","Ratio of time On vs Reference");
		rSigRef.setToolTip("Ratio of observing time spent on-source/on-frequency to that spent on a reference source/frequency");
		rSigRef.setValue("1");
		
		//nOverlap = new GeneralCombo("nOverlap","Enter number of spectral windows centered",  new ArrayList<String>(Arrays.asList("1","2")));
		averagePol = new CheckBox();
		averagePol.setBoxLabel("Average Orthognal Polarizations");
		averagePol.setLabelSeparator("");
		differenceSignal =  new CheckBox();
		differenceSignal.setBoxLabel("Difference Signal and Reference Observations");
		differenceSignal.setLabelSeparator("");
		differenceSignal.setValue(true);
		nAverageRef = new GeneralText("nAverageRef","Number of Reference Observations");
		nAverageRef.setToolTip("In data reduction you have the option to average multiple reference observations in order to improve the noise. Enter number of reference observations that will be averaged together.");
		nAverageRef.setValue("1");
		
		smoothing = new RadioGroup("smoothing");
		smoothing.setFieldLabel("Smooth On-source Data to a Desired");
		smoothing.setName("smoothing");
		smoothing.setId("smoothing");
		smoothing.setOrientation(Orientation.VERTICAL);
		
		Radio choice = new Radio();
		choice.setBoxLabel("Velocity Resolution in the Rest Frame");
		choice.setValueAttribute("velocity_resolution_rest");
		choice.setName("velocity_resolution_rest");
		choice.setValue(true);
		smoothing.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Frequency Resolution in the Topocentric");
		choice.setValueAttribute("frequency_resolution_topo");
		choice.setName("frequency_resolution_topo");
		smoothing.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("Frequency Resolution in the Rest Frame");
		choice.setValueAttribute("frequency_resolution_rest");
		choice.setName("frequency_resolution_rest");
		smoothing.add(choice);
		
		resolution = new GeneralText("smoothing_resolution", "Desired Resolution (km/s)");
		resolution.setMaxLength(6);
		
		smoothing.addListener(Events.Change, new Listener<FieldEvent> () {

			@Override
			public void handleEvent(FieldEvent be) {
				if (smoothing.getValue().getValueAttribute().equals("velocity_resolution_rest")) {
					resolution.setFieldLabel("Deired Resolution (km/s)");
				} else {
					resolution.setFieldLabel("Deired Resolution (MHz)");
				}
			}
			
		});
		
		smoothing_factor_inst = new Label("To improve signal-to-noise you can smooth reference observations to a resolution that is a few times courser than the signal observation.  Select the factor by which you want to smooth the reference observation:");
				
		smoothing_factor = new RadioGroup("smoothing_factor");
		smoothing_factor.setName("smoothing_factor");
		smoothing_factor.setId("smoothing_factor");
		smoothing_factor.setFieldLabel("Smoothing Factor");
		
		choice = new Radio();
		choice.setBoxLabel("1");
		choice.setValue(true);
		choice.setValueAttribute("1");
		choice.setName("1");
		smoothing_factor.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("2");
		choice.setValueAttribute("2");
		choice.setName("2");
		smoothing_factor.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("4");
		choice.setValueAttribute("4");
		choice.setName("4");
		smoothing_factor.add(choice);
		
		choice = new Radio();
		choice.setBoxLabel("8");
		choice.setValueAttribute("8");
		choice.setName("8");
		smoothing_factor.add(choice);
		
		smoothingFieldSet = new FieldSet();
		smoothingFieldSet.setHeading("Smoothing");
		FormLayout layout = new FormLayout();  
		layout.setLabelWidth(200);  
		smoothingFieldSet.setLayout(layout);
		
		//initial state
		nOnPerOff.hide();
		rSigRef.hide();
		smoothing.hide();
		resolution.hide();
		smoothing_factor_inst.hide();
		smoothing_factor.hide();
		smoothingFieldSet.hide();

		FormData fd = new FormData(60, 20);
		
		//attaching fields
		add(nOnPerOff, fd);
		add(rSigRef, fd);
		add(nAverageRef, fd);
		//add(nOverlap);
		add(averagePol);
		add(differenceSignal);
		
		smoothingFieldSet.add(smoothing);
		smoothingFieldSet.add(resolution);
		smoothingFieldSet.add(smoothing_factor_inst);
		smoothingFieldSet.add(smoothing_factor);
		add(smoothingFieldSet);
		
		//attaching fields

	}


//	class HandleSmoothing implements Listener<FieldEvent> {
//		public void handleEvent(FieldEvent fe) {
//			if (smoothing.getValue()) {
//				smoothingResolution.show();
//				bw.show();
//				bwRef.show();
//			} else {
//				smoothingResolution.hide();
//				bw.hide();
//				bwRef.hide();
//			}
//		}
//	}


	
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
		
		} else if (name.equals("mode")) {
			if (value.equals("Spectral Line")) {
				smoothing.show();
				resolution.show();
				smoothing_factor_inst.show();
				smoothing_factor.show();
				smoothingFieldSet.show();
			} else {
				smoothing.hide();
				resolution.hide();
				smoothing_factor_inst.hide();
				smoothing_factor.hide();
				smoothingFieldSet.hide();
			}
			
			if (value.equals("Total Power")) {
				differenceSignal.hide();
			} else {
				differenceSignal.show();
			}

		} else if (name.equals("polarization")) {
			if (value.equals("Dual")) {
				averagePol.setValue(true);
			} else {
				averagePol.setValue(false);				
			}
		}
		
//		if (name.equals("backend")) {
//			if (value.equals("Spectrometer")) {
//				nOverlap.show();
//			} else {
//				nOverlap.hide();
//			}
//
//		}

	}
	
	public void validate() {

	}
	
	

}