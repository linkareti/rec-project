/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.voscilador.displays;

/**
 * 
 * @author andre
 */
public class ZTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7820332641343365092L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public ZTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(4);
	}

}
