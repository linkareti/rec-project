/*
 * CharacteristicChart.java
 *
 * Created on 19 de Fevereiro de 2013
 */

package pt.utl.ist.elab.client.paschen.displays;

/**
 * 
 * @author jloureiro
 */
public class CharacteristicChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2844737794974151743L;

	/** Creates a new instance of CharacteristicChart */
	public CharacteristicChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
