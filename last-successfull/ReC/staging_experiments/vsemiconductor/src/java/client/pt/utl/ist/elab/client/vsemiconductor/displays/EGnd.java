/*
 * EGnd.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de Eg em funcao de nd
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queiró e Nuno Fernandes
 */
public class EGnd extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public EGnd() {
		super();
		setChannelDisplayX(6);
		setChannelDisplayY(1);
	}

	public String getName() {
		return "Energy Gap em funcao do numero de dadores";
	}
}