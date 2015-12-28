/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.voscilador.displays;

/**
 * 
 * @author andre
 */
public class VelYTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1973620374198272996L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VelYTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(6);
	}

}
