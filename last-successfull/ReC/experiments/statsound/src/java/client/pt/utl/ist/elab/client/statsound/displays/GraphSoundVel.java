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
public class GraphSoundVel extends MyDefaultXYExperimentGraph {
	/** Creates a new instance of PistonRMS */
	public GraphSoundVel() {
		super();
		setChannelDisplayX(2);
		setChannelDisplayYArray(new int[] { 3, 4 });
	}

	public String getName() {
		return "Chart Sound velocity";
	}
}
