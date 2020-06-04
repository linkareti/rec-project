/* 
 * WPBarcelonaGraphPeriod.java created on 1 May 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.wpbarcelona;

import com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph;

/**
 * 
 * @author rneto - Linkare TI
 */
public class WPBarcelonaGraphPeriod extends DefaultXYExperimentGraph{

	/** Generated UID */
	private static final long serialVersionUID = 974560783079598747L;
	
	/**
	 * Creates the <code>WPBarcelonaGraphPeriod</code>.
	 */
	public WPBarcelonaGraphPeriod() {
		super();
		setChannelDisplayX(0); // Número da amostra
		setChannelDisplayY(1); // channel 1 = Período medido
	}
}
