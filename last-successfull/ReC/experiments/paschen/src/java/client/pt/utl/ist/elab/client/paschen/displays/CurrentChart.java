/*
 * CurrentChart.java
 *
 * Created on 19 de Fevereiro de 2013
 */

package pt.utl.ist.elab.client.paschen.displays;

/**
 * 
 * @author jloureiro
 */
public class CurrentChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6205772325368911069L;

	/** Creates a new instance of CurrentChart */
	public CurrentChart() {
		super();
		setChannelDisplayX(3);
		setChannelDisplayY(1);
	}

}
