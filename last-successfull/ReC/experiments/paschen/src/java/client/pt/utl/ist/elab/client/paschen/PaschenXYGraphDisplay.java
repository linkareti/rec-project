/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.utl.ist.elab.client.paschen;

/**
 *
 * @author jloureiro
 */
public class PaschenXYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {
    
    	/**
	 * 
	 */

	/** Creates a new instance of PaschenXYGraphDisplay */
	public PaschenXYGraphDisplay() {
		super();
		setChannelDisplayX(0);  //Channel 0 - Voltage 
		setChannelDisplayY(1);  //Channel 1 - Current
	}
    
}
