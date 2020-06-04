/* 
 * OpticaGraphDisplay.java created on 13 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.wputfsmvap;

import com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph;

/**
 * 
 * @author npadriano
 */
public class WorldPendulumGraphDisplay extends DefaultXYExperimentGraph{

	/** Generated UID */
	private static final long serialVersionUID = 974560783079598747L;

	/**
	 * Creates the <code>OpticaGraphDisplay</code>.
	 */
	public WorldPendulumGraphDisplay() {
		super();
		setChannelDisplayX(0); // Número da amostra
		setChannelDisplayY(3); // g medido
	}
}