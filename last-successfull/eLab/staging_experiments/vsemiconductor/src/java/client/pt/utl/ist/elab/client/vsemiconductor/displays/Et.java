/*
 * Et.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de E em funcao de t
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class Et extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public Et() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(3);
	}

	public String getName() {
		return "Campo Electrico em funcao da temperatura";
	}
}