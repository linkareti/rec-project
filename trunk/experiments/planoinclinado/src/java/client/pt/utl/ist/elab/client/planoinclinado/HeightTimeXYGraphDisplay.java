/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.planoinclinado;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HeightTimeXYGraphDisplay extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7106640143251132958L;

	/** Creates a new instance of PVXYGraphDisplay */
	public HeightTimeXYGraphDisplay() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayY(0);
	}

}
