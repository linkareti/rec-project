/*
 * VelTimeChart.java
 *
 * Created on 20 de Fevereiro de 2005, 22:14
 */

package pt.utl.ist.elab.client.vm3.displays;

/**
 * 
 * @author n0dP2
 */
public class VelTimeChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4883371892029497178L;

	/** Creates a new instance of VelTimeChart */
	public VelTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(2);
	}

}
