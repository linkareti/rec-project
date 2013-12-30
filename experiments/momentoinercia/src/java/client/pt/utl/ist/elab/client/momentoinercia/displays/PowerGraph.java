/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.momentoinercia.displays;

import com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class PowerGraph extends MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2094904581561530632L;

	/** Creates a new instance of Sensor1Graph */
	public PowerGraph() {
		setChannelDisplayX(2);
		setChannelDisplayY(1);
		setUpdateFrequency(10);
	}

	@Override
	public String getName() {
		return "Potência dissipada";
	}
}
