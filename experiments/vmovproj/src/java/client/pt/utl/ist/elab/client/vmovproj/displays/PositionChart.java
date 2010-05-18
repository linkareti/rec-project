/*
 * PositionChart.java
 *
 * Created on 20 de Mar�o de 2005, 15:21
 */

package pt.utl.ist.elab.client.vmovproj.displays;

import pt.utl.ist.elab.virtual.guipack.graphs.GraphSamplesFunction;
import pt.utl.ist.elab.virtual.guipack.graphs.MultSeriesXYExperimentGraphExtended;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 * 
 * @author nomead
 */
public class PositionChart extends MultSeriesXYExperimentGraphExtended implements GraphSamplesFunction {

	private boolean isAbs;

	/** Creates a new instance of PositionChart */
	public PositionChart() {
		super();
	}

	public double getValueX(int ch, double val) {
		return val;
	}

	public double getValueY(int ch, double val) {
		if (isAbs)
			return Math.abs(val);
		return val;
	}

	public void dataModelStartedNoData() {
		HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		setChannelDisplayX(6);
		if (header.getSelectedHardwareParameterValue("posModulus").trim().equals("1") ? true : false)
			setChannelDisplayYArray(new int[] { 0, 1, 2, 10 });
		else
			setChannelDisplayYArray(new int[] { 0, 1, 2 });

		isAbs = header.getSelectedHardwareParameterValue("posAbs").trim().equals("1") ? true : false;
		super.dataModelStartedNoData();
	}

	private ExpDataModel model = null;

	public void setExpDataModel(ExpDataModel model) {
		super.setExpDataModel(model);
		if (this.model != null)
			this.model.removeExpDataModelListener(this);
		this.model = model;
		if (this.model != null)
			this.model.addExpDataModelListener(this);

	}
}
