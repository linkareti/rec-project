/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.boylemariotte;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PVXYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5818299759208511328L;

	/** Creates a new instance of PVXYGraphDisplay */
	public PVXYGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
