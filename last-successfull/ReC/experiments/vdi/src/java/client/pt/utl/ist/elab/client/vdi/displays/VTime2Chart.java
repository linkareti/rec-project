/*
 * VTimeChart.java
 *
 * Created on April 03, 2005, 3:40 PM
 */

package pt.utl.ist.elab.client.vdi.displays;

/**
 * 
 * @author Queiro'
 */
public class VTime2Chart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2464533178382197127L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VTime2Chart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(6);
	}
}