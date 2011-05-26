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
public class X1X2Y1Y2TimeChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 714942187731728387L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public X1X2Y1Y2TimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 1, 2, 3, 4 });
	}

}
