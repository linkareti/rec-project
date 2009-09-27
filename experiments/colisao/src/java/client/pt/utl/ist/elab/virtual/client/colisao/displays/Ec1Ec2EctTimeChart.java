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
public class Ec1Ec2EctTimeChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of AnguloIntensidadeChart */
	public Ec1Ec2EctTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] { 11, 12, 13 });
	}

}
