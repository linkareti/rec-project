/*
 * PistonRMS.java
 *
 * Created on October 13, 2003, 5:55 PM
 */

package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class GraphFreqRMS extends MyDefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2036950391221540275L;
	private static final String CHART_NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.2");

	/** Creates a new instance of PistonRMS */
	public GraphFreqRMS() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayYArray(new int[] { 3, 4 });
	}

	@Override
	public String getName() {
		return GraphFreqRMS.CHART_NAME;
	}

	@Override
	protected String getChartName(final HardwareAcquisitionConfig header) {
		return getName();
	}
}
