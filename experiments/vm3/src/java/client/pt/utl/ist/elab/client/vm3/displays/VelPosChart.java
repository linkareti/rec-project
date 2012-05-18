/*
 * PosVelChart.java
 *
 * Created on 20 de Fevereiro de 2005, 22:13
 */

package pt.utl.ist.elab.client.vm3.displays;

/**
 * 
 * @author n0dP2
 */
public class VelPosChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6118365395426651450L;

	/** Creates a new instance of PosVelChart */
	public VelPosChart() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayY(2);
	}

}
