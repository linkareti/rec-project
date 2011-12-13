/*
 * IntensityXChart.java
 *
 * Created on ?????
 */

package pt.utl.ist.elab.client.vyounginterf.displays;

/**
 * 
 * @author Emanuel Antunes
 */
public class IntensityXChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9136750960975902084L;

	/** Creates a new instance of IntensityXChart */
	public IntensityXChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}
}
