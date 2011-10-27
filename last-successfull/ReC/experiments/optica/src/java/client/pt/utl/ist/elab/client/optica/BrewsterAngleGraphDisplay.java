/* 
 * BrewsterAngleGraphDisplay.java created on 11 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.optica;

import com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author npadriano
 */
public class BrewsterAngleGraphDisplay extends MultSeriesXYExperimentGraph {

	/** Generated UID */
	private static final long serialVersionUID = 6865380144573073714L;

	/**
	 * Creates the <code>BrewsterAngleGraphDisplay</code>.
	 */
	public BrewsterAngleGraphDisplay() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayYArray(new int[] { 2 });
	}

}
