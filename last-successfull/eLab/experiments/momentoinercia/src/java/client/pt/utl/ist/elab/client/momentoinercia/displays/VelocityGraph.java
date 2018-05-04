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
public class VelocityGraph extends MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3091863273206698440L;

	/** Creates a new instance of Sensor1Graph */
	public VelocityGraph() {
		setChannelDisplayX(2);
		setChannelDisplayY(0);
		setUpdateFrequency(10);
	}

	@Override
	public String getName() {
		return "Velocidade angular";
	}
}
