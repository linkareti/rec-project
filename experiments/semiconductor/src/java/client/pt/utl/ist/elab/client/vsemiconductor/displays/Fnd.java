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
 * @author Pedro Queir� e Nuno Fernandes
 */
public class Fnd extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of W */
	public Fnd() {
		super();
		setChannelDisplayX(6);
		setChannelDisplayY(5);
	}

	public String getName() {
		return "Fermi em funcao do numero de dadores";
	}
}