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
public class PETimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8086038392778136852L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public PETimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(12);
	}

}
