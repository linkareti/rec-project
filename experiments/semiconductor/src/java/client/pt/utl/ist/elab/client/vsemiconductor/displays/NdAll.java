/*
 * EGAll.java
 *
 * Created on June 11, 2004, 12:33 PM
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 * 
 * @author André Neto - LEFT - IST
 */

public class NdAll extends com.linkare.rec.impl.baseUI.graph.MultiPanelExperimentGraph {

	/** Creates a new instance of EGAll */
	public NdAll() {
		super(new Object[] { new EGnd(), new End(), new VBInd(), new Wnd(), new Fnd() });
	}

	public String getName() {
		return "Varia\u00e7\u00e3o com Nd";
	}
}
