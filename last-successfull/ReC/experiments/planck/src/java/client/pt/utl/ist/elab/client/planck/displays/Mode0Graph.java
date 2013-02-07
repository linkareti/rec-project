/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.planck.displays;

import com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Mode0Graph extends MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4140016764770630928L;

	/** Creates a new instance of Sensor1Graph */
	public Mode0Graph() {
		setChannelDisplayX(1);
		setChannelDisplayY(2);
		setUpdatePercentage(10);
	}

	@Override
	public String getName() {
		return "Modo Varrimento";
	}
}
