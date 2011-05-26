/*
 * B1I1Chart.java
 *
 * Created on April 03, 2005, 3:40 PM
 */

package pt.utl.ist.elab.client.vbs.displays;

/**
 * 
 * @author Queiro'
 */
public class B1I1Chart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3459589525848400421L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public B1I1Chart() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayY(2);
	}
}