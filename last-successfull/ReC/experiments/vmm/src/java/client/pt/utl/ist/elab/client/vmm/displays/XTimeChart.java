/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.vmm.displays;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class XTimeChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1376743686449155321L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public XTimeChart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(1);
	}

}
