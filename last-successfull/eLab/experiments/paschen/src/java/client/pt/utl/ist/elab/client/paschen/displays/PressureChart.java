/*
 * PressureChart.java
 *
 * Created on 19 de Fevereiro de 2013
 */

package pt.utl.ist.elab.client.paschen.displays;

/**
 * 
 * @author jloureiro
 */
public class PressureChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2028815984045068552L;

	/** Creates a new instance of PressureChart */
	public PressureChart() {
		super();
		setChannelDisplayX(3);
		setChannelDisplayY(2);
	}

}
