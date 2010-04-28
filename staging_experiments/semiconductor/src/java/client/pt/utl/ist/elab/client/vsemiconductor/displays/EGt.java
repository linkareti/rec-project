/*
 * EGt.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de Eg em funcao de t
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author Pedro Queir� e Nuno Fernandes
 */
public class EGt extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public EGt() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(1);
	}

	public String getName() {
		return "Energy Gap em funcao da temperatura";
	}
}