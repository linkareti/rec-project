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

	/**
	 * 
	 */
	private static final long serialVersionUID = 5675190300048282542L;
	private boolean isAbs;

	/** Creates a new instance of PositionChart */
	public PositionChart() {
		super();
	}

	@Override
	public double getValueX(final int ch, final double val) {
		return val;
	}

	@Override
	public double getValueY(final int ch, final double val) {
		if (isAbs) {
			return Math.abs(val);
		}
		return val;
	}

	@Override
	public void dataModelStartedNoData() {
		final HardwareAcquisitionConfig header = model.getAcquisitionConfig();

		setChannelDisplayX(6);
		if (header.getSelectedHardwareParameterValue("posModulus").trim().equals("1") ? true : false) {
			setChannelDisplayYArray(new int[] { 0, 1, 2, 10 });
		} else {
			setChannelDisplayYArray(new int[] { 0, 1, 2 });
		}

		isAbs = header.getSelectedHardwareParameterValue("posAbs").trim().equals("1") ? true : false;
		super.dataModelStartedNoData();
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}
		super.setExpDataModel(model);
	}
}
