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
public class VelZTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3428648717600615092L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VelZTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(7);
	}

}
