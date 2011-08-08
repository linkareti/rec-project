/*
 * PosTimeChart.java
 *
 * Created on 20 de Fevereiro de 2005, 22:13
 */

package pt.utl.ist.elab.client.vm3.displays;

/**
 * 
 * @author n0dP2
 */
public class PosTimeChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2400972701539423406L;

	/** Creates a new instance of PosTimeChart */
	public PosTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
