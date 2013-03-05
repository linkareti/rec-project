/*
 * DistanceChart.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vtiro.displays;

/**
 * 
 * @author nomead
 */
public class DistanceChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6042510214760770582L;

	/** Creates a new instance of DistanceChart */
	public DistanceChart() {
		super();
		setChannelDisplayX(6);
		setChannelDisplayY(5);
	}

}
