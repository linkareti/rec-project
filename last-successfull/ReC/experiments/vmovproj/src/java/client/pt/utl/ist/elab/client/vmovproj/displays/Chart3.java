/*
 * Chart3.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vmovproj.displays;

import pt.utl.ist.elab.client.vmovproj.StringUtils;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 * 
 * @author nomead
 */
public class Chart3 extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1232626104570812598L;

	/** Creates a new instance of Chart3 */
	public Chart3() {
		super();
	}

	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		final String[] graph3 = StringUtils.splitArroundPoint(header.getSelectedHardwareParameterValue("graph3"));

		if (graph3[0].equalsIgnoreCase("X")) {
			setChannelDisplayX(0);
		} else if (graph3[0].equalsIgnoreCase("Y")) {
			setChannelDisplayX(1);
		} else if (graph3[0].equalsIgnoreCase("Z")) {
			setChannelDisplayX(2);
		} else if (graph3[0].equalsIgnoreCase("| r |")) {
			setChannelDisplayX(10);
		} else if (graph3[0].equalsIgnoreCase("Vx")) {
			setChannelDisplayX(3);
		} else if (graph3[0].equalsIgnoreCase("Vy")) {
			setChannelDisplayX(4);
		} else if (graph3[0].equalsIgnoreCase("Vz")) {
			setChannelDisplayX(5);
		} else if (graph3[0].equalsIgnoreCase("| v |")) {
			setChannelDisplayX(11);
		} else if (graph3[0].equalsIgnoreCase("t")) {
			setChannelDisplayX(6);
		}

		if (graph3[1].equalsIgnoreCase("X")) {
			setChannelDisplayY(0);
		} else if (graph3[1].equalsIgnoreCase("Y")) {
			setChannelDisplayY(1);
		} else if (graph3[1].equalsIgnoreCase("Z")) {
			setChannelDisplayY(2);
		} else if (graph3[1].equalsIgnoreCase("| r |")) {
			setChannelDisplayY(10);
		} else if (graph3[1].equalsIgnoreCase("Vx")) {
			setChannelDisplayY(3);
		} else if (graph3[1].equalsIgnoreCase("Vy")) {
			setChannelDisplayY(4);
		} else if (graph3[1].equalsIgnoreCase("Vz")) {
			setChannelDisplayY(5);
		} else if (graph3[1].equalsIgnoreCase("| v |")) {
			setChannelDisplayY(11);
		} else if (graph3[1].equalsIgnoreCase("t")) {
			setChannelDisplayY(6);
		}

		super.dataModelStartedNoData();
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		super.setExpDataModel(model);
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}

	}

}