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
public class XTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8791782343788647574L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public XTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(2);
	}

}