/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.pv;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PVXYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177968201292210791L;

	/** Creates a new instance of PVXYGraphDisplay */
	public PVXYGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
