/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.rayleigh.displays;

import com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class PosSigGraph1 extends MultSeriesXYExperimentGraph {

	/** Creates a new instance of Sensor1Graph */
	public PosSigGraph1() {
		setChannelDisplayX(0);
		setChannelDisplayY(1);
		setUpdateFrequency(1);
	}

	public String getName() {
		return "Grafico 1";
	}
}