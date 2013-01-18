/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.utl.ist.elab.client.paschen;

/**
 *
 * @author jloureiro
 */
public class PaschenPressureGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */

	/** Creates a new instance of PaschenPressureGraphDisplay */
	public PaschenPressureGraphDisplay() {
		super();
		setChannelDisplayX(0);  //Channel 0 - Voltage 
		setChannelDisplayY(2);  //Channel 2 - Preasure from dual gauge
	}

}
