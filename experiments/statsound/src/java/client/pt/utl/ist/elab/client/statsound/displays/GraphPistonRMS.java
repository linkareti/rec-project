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
public class GraphPistonRMS extends MyDefaultXYExperimentGraph {

	private static final String CHART_NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.4");

	/** Creates a new instance of PistonRMS */
	public GraphPistonRMS() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 3, 4 });
	}

	@Override
	public String getName() {
		return CHART_NAME;
	}

	@Override
	protected String getChartName(HardwareAcquisitionConfig header) {
		return getName();
	}
}
