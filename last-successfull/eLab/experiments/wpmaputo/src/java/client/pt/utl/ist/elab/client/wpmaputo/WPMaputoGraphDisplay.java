/* 
 * OpticaGraphDisplay.java created on 13 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.wpmaputo;

import com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph;

/**
 * 
 * @author npadriano
 */
public class WPMaputoGraphDisplay extends DefaultXYExperimentGraph{

	/** Generated UID */
	private static final long serialVersionUID = 974560783079598747L;

	/**
	 * Creates the <code>OpticaGraphDisplay</code>.
	 */
	public WPMaputoGraphDisplay() {
		super();
		setChannelDisplayX(0); // Número da amostra
		setChannelDisplayY(3); // g medido
	}
}