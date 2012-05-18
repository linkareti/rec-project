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
public class XYChart extends com.linkare.rec.impl.ui.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8730771619728980091L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public XYChart() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayY(2);

	}

}
