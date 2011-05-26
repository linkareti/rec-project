/*
 * XChart.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vcartpole.displays;

/**
 * 
 * @author nomead
 */
public class XChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 372965724684815857L;

	/** Creates a new instance of XChart */
	public XChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(2);
	}

}
