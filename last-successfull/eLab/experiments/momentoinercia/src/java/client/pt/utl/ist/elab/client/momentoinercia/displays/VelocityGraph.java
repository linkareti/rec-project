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
public class VelocityGraph extends MultSeriesXYExperimentGraph {

	/** Creates a new instance of Sensor1Graph */
	public VelocityGraph() {
		setChannelDisplayX(2);
		setChannelDisplayY(0);
		setUpdatePercentage(10);
	}

	public String getName() {
		return "Velocidade angular";
	}
}
