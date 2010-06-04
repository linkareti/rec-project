/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.momentoinercia.displays;

import com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class PowerGraph extends MultSeriesXYExperimentGraph {

	/** Creates a new instance of Sensor1Graph */
	public PowerGraph() {
		setChannelDisplayX(2);
		setChannelDisplayY(1);
		setUpdatePercentage(10);
	}

	public String getName() {
		return "Pot�ncia dissipada";
	}
}
