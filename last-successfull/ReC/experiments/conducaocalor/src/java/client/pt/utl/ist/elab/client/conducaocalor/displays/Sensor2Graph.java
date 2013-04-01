/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.conducaocalor.displays;

import com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Sensor2Graph extends MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2235631884338702290L;

	/** Creates a new instance of Sensor1Graph */
	public Sensor2Graph() {
		super();
		setChannelDisplayX(9);
		setChannelDisplayYArray(new int[] { 1, 4, 7 });
	}

	@Override
	public String getName() {
		return "Sensor 2";
	}
}
