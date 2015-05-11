/*
 * Theta1.java
 *
 * Created on 13 February 2004, 20:23
 */

package pt.utl.ist.elab.client.vdpendulum.displays;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Theta1 extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/** Creates a new instance of Theta1 */
	public Theta1() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

	public String getName() {
		return "Grafico 1";
	}
}
