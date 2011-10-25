/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.conducaocalor.displays;

import com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Sensor3Graph extends MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7292958049603795684L;

	/** Creates a new instance of Sensor1Graph */
	public Sensor3Graph() {
		super();
		setChannelDisplayX(9);
		setChannelDisplayYArray(new int[] { 2, 5, 8 });
	}

	@Override
	public String getName() {
		return "Sensor 3";
	}
}
