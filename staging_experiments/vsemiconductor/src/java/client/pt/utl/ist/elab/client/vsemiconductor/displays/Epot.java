/*
 * Epot.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de E em funcao de pot
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class Epot extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public Epot() {
		super();
		setChannelDisplayX(8);
		setChannelDisplayY(3);
	}

	public String getName() {
		return "Campo Electrico em funcao do potencial";
	}
}