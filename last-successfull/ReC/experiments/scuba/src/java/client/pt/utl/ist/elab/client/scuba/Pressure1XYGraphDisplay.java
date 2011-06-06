/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.scuba;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Pressure1XYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6571232162158803668L;

	/** Creates a new instance of PVXYGraphDisplay */
	public Pressure1XYGraphDisplay() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(0);
	}

}
