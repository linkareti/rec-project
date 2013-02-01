/*
 * Wnd.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de W em funcao do numero de dadores
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class Wnd extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public Wnd() {
		super();
		setChannelDisplayX(6);
		setChannelDisplayY(0);
	}

	public String getName() {
		return "Largura da zona de Deplecao em funcao do numero de dadores";
	}
}