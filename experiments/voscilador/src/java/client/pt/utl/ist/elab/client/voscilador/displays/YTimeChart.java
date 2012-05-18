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
public class YTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5065873656226904594L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public YTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(3);
	}

}
