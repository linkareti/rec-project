/*
 * VBInd.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de vBI em funcao de nd
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class VBInd extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public VBInd() {
		super();
		setChannelDisplayX(6);
		setChannelDisplayY(2);
	}

	public String getName() {
		return "Potencial Intrinseco em funcao do numero de dadores";
	}
}