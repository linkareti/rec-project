/*
 * Wt.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de W em funcao da temperatura
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class Wt extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public Wt() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(0);
	}

	public String getName() {
		return "Largura da zona de Deplecao em funcao da temperatura";
	}
}