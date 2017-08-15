/*
 * Mag3DXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.mag3d;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Mag3DXYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177968201292210791L;

	/** Creates a new instance of Mag3DXYGraphDisplay */
	public Mag3DXYGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
