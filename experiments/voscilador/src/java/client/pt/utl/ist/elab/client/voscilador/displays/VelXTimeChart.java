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
public class VelXTimeChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7410561221247557016L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VelXTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(5);
	}

}
