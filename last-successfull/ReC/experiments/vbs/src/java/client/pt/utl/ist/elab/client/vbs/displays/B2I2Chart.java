/*
 * B2I2Chart.java
 *
 * Created on April 03, 2005, 3:40 PM
 */

package pt.utl.ist.elab.client.vbs.displays;

/**
 * 
 * @author Queiro'
 */
public class B2I2Chart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4555510135304280443L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public B2I2Chart() {
		super();
		setChannelDisplayX(5);
		setChannelDisplayY(3);
	}
}