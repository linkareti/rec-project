/*
 * CCRawXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.condensadorcilindrico;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class CCRawXYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8245457534808047987L;

	/** Creates a new instance of CCRawXYGraphDisplay */
	public CCRawXYGraphDisplay() {
		super();
		setChannelDisplayX(2);
		setChannelDisplayY(0);
	}

}
