/*
 * BTimeChart.java
 *
 * Created on April 03, 2005, 3:40 PM
 */

package pt.utl.ist.elab.client.vbs.displays;

/**
 * 
 * @author Queiro'
 */
public class BTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6644240122314922711L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public BTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}
}