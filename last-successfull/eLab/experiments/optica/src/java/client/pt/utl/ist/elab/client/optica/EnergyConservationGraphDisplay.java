/* 
 * EnergyConservationGraphDisplay.java created on 10 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.optica;

import com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph;

/**
 * 
 * @author npadriano
 */
public class EnergyConservationGraphDisplay extends DefaultXYExperimentGraph {

	/** Generated UID */
	private static final long serialVersionUID = -6421123971928950564L;

	/**
	 * Creates the <code>EnergyConservationGraphDisplay</code>.
	 */
	public EnergyConservationGraphDisplay() {
		super();
		setChannelDisplayX(1); // Ângulo sensor óptico
		setChannelDisplayY(2); // ADC
	}

}
