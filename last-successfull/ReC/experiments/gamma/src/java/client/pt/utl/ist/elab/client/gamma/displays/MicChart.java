/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.gamma.displays;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class MicChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6673594847895572678L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public MicChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(2);
	}

}