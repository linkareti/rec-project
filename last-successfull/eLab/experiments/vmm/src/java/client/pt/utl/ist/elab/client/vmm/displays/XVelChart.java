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
public class XVelChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7730826508876935576L;

	/** Creates a new instance of AnguloIntensidadeChart */
	public XVelChart() {
		super();
		setChannelDisplayX(1);
		setChannelDisplayY(2);
	}

}
