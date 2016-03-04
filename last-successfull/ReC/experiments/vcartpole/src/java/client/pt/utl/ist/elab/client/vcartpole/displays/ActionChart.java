/*
 * ActionChart.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.client.vcartpole.displays;

/**
 * 
 * @author nomead
 */
public class ActionChart extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3215997957386721916L;

	/** Creates a new instance of ActionChart */
	public ActionChart() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayYArray(new int[] { 1, 3, 2, 5 });
	}

}
