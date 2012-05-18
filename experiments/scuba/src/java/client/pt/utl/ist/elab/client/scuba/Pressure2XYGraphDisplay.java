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
public class Pressure2XYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6349303891819450971L;

	/** Creates a new instance of PVXYGraphDisplay */
	public Pressure2XYGraphDisplay() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(1);
	}

}
