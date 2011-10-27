/* 
 * PinHoleGraphDisplay.java created on 13 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.pinhole;

import com.linkare.rec.impl.baseUI.graph.MultSeriesXYInBlockExperimentGraph;
import pt.utl.ist.elab.client.pinhole.PinHoleCustomizerPanel;

/**
 * 
 * @author npadriano
 */
public class PinHoleGraphDisplay extends MultSeriesXYInBlockExperimentGraph {

	/** Generated UID */
	private static final long serialVersionUID = 974560783079598747L;

	/**
	 * Creates the <code>PinHoleGraphDisplay</code>.
	 */
	public PinHoleGraphDisplay() {
		super(PinHoleCustomizerPanel.NUMBER_OF_SAMPLES_FOR_SERIES);
		setChannelDisplayX(1); // Ângulo sensor óptico
		setChannelDisplayY(2); // ADC
	}
}
