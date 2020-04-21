package pt.utl.ist.elab.client.wppucrio;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.ui.graph.DefaultExperimentBarGraph;

/* 
 * WorldPendulumHistogram.java created on 21 de Out de 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 * @author João Loureiro - IPFN
 */
public class WorldPendulumHistogram extends DefaultExperimentBarGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9032132978585161312L;

	/**
	 * Creates the <code>WorldPendulumHistogram</code>.
	 */
	public WorldPendulumHistogram() {
		super();
		setChannelDisplay(1);
		setNumberAxisName(ReCResourceBundle.findStringOrDefault("wppucrio$display.graph.histogram.numberaxis","Hits"));
	}

	
	
}
