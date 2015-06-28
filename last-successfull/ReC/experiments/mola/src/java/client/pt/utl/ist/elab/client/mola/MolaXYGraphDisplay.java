/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package mola.src.java.client.pt.utl.ist.elab.client.mola;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MolaXYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177968201292210791L;

	/** Creates a new instance of PVXYGraphDisplay */
	public MolaXYGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
