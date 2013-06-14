/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.utl.ist.elab.client.planck;

/**
 *
 * @author ruben
 */
public class VoltageTimeGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {
        
        /**
	 * Creates the <code>VoltageTimeGraphDisplay</code>.
	 */
        public VoltageTimeGraphDisplay() {
		super();
		setChannelDisplayX(0); // Tempo
		setChannelDisplayY(1); // Tensão
	}
}
