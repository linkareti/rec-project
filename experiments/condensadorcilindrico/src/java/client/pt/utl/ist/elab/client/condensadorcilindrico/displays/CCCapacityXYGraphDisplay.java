/*
 * CCRawXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.condensadorcilindrico.displays;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class CCCapacityXYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {
	private static final long serialVersionUID = -3156779344699977988L;

	/** Creates a new instance of CCRawXYGraphDisplay */
	public CCCapacityXYGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}