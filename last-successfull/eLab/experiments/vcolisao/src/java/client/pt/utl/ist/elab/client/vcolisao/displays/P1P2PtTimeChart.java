/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.vcolisao.displays;

/**
 * 
 * @author Emanuel A.
 */
public class P1P2PtTimeChart extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7028830242719177308L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public P1P2PtTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 5, 6, 7, 8, 9, 10 });
	}

}