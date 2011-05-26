/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.vlooping.displays;

/**
 * 
 * @author Emanuel Antunes
 */
public class XYTimeChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5935772358389328281L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public XYTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 1, 2 });
	}

}
