/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.utl.ist.elab.client.discos;

/**
 *
 * @author ruben
 */
public class VelocityTimeGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {
        
        /**
	 * Creates the <code>VelocityTimeGraphDisplay</code>.
	 */
        public VelocityTimeGraphDisplay() {
		super();
		setChannelDisplayX(0); // Tempo
		setChannelDisplayY(1); // RPM
	}
}
