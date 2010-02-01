/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.virtual.client.colisao.displays;

/**
 * 
 * @author Emanuel A.
 */
public class P1P2PtTimeChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of AnguloIntensidadeChart */
	public P1P2PtTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 5, 6, 7, 8, 9, 10 });
	}

}
