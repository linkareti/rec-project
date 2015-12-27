/*
 * Wpot.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de W em funcao do potencial externo
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class Wpot extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public Wpot() {
		super();
		setChannelDisplayX(8);
		setChannelDisplayY(0);
	}

	public String getName() {
		return "Largura da zona de Deplecao em funcao do potencial externo";
	}
}