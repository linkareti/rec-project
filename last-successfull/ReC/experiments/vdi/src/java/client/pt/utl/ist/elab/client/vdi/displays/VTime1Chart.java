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
public class VTime1Chart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5171114960930617144L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public VTime1Chart() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayY(5);
	}
}