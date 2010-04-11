/*
 * PistonRMS.java
 *
 * Created on October 13, 2003, 5:55 PM
 */

package pt.utl.ist.elab.client.statsound.displays;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class GraphFreqRMS extends MyDefaultXYExperimentGraph {
	/** Creates a new instance of PistonRMS */
	public GraphFreqRMS() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayYArray(new int[] { 2, 3 });
	}

	public String getName() {
		return "Chart Frequency vs RMS";
	}
}
