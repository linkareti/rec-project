/*
 * ThetaChart.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vcartpole.displays;

/**
 * 
 * @author nomead
 */
public class ThetaChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7199137166280304856L;

	/** Creates a new instance of ThetaChart */
	public ThetaChart() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayY(3);
	}

}
