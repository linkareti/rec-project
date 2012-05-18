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
public class Ec1Ec2EctTimeChart extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2065340048650422623L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public Ec1Ec2EctTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 11, 12, 13 });
	}

}
