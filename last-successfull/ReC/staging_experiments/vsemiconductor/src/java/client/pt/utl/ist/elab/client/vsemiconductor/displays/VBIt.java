/*
 * VBIt.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de vBI em funcao de t
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class VBIt extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public VBIt() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(2);
	}

	public String getName() {
		return "Potencial Intrinseco em funcao da temperatura";
	}
}