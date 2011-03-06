/* 
 * OpticaGraphDisplay.java created on 13 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.optica;

import com.linkare.rec.impl.baseUI.graph.MultSeriesXYInBlockExperimentGraph;

/**
 * 
 * @author npadriano
 */
public class OpticaGraphDisplay extends MultSeriesXYInBlockExperimentGraph {

	/** Generated UID */
	private static final long serialVersionUID = 974560783079598747L;

	private static final int DEFAULT_UPDATE_FREQUENCY = 10;

	/**
	 * Creates the <code>OpticaGraphDisplay</code>.
	 */
	public OpticaGraphDisplay() {
		super(OpticaCustomizerPanel.NUMBER_OF_SAMPLES_FOR_SERIES);
		setChannelDisplayX(1); // Ângulo sensor óptico
		setChannelDisplayY(2); // ADC
		setUpdateFrequency(DEFAULT_UPDATE_FREQUENCY);
	}
}