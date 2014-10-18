/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.vmm.displays;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class VTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9080215477686118733L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(2);
	}

}
