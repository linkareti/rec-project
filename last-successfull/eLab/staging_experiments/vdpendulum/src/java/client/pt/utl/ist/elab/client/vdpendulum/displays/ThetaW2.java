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
public class ThetaW2 extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/** Creates a new instance of Theta1 */
	public ThetaW2() {
		super();
		setChannelDisplayX(2);
		setChannelDisplayY(4);
	}

	public String getName() {
		return "Grafico 6";
	}
}
