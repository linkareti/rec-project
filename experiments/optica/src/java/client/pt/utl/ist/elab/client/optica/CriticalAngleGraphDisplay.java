/* 
 * CriticalAngleGraphDisplay.java created on 11 Jan 2011
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
public class CriticalAngleGraphDisplay extends MultSeriesXYExperimentGraph {

	/** Generated UID */
	private static final long serialVersionUID = -38458033188305378L;

	/**
	 * Creates the <code>CriticalAngleGraphDisplay</code>.
	 */
	public CriticalAngleGraphDisplay() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayYArray(new int[] { 2 });
	}

}
