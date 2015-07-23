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
public class METimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -723312698351999916L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public METimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(10);
	}

}
