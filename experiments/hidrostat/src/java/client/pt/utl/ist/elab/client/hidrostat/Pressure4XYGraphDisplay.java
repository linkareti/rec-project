/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.hidrostat;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Pressure4XYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -53801812230528996L;

	/** Creates a new instance of PVXYGraphDisplay */
	public Pressure4XYGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(4);
	}

}