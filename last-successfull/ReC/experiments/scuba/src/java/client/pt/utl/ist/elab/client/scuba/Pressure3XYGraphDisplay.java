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
public class Pressure3XYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1597619994016594960L;

	/** Creates a new instance of PVXYGraphDisplay */
	public Pressure3XYGraphDisplay() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(2);
	}

}
