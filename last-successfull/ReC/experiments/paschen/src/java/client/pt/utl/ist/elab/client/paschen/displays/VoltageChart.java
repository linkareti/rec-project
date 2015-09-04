/*
 * VoltageChart.java
 *
 * Created on 19 de Fevereiro de 2013
 */

package pt.utl.ist.elab.client.paschen.displays;

/**
 * 
 * @author jloureiro
 */
public class VoltageChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2623391115537596478L;

	/** Creates a new instance of VoltageChart */
	public VoltageChart() {
		super();
		setChannelDisplayX(3);
		setChannelDisplayY(0);
	}

}
