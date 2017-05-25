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
public class VelTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4635707239199262347L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VelTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(8);
	}

}
