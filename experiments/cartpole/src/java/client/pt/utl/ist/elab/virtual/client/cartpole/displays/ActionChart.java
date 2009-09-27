/*
 * ActionChart.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.virtual.client.cartpole.displays;

/**
 * 
 * @author nomead
 */
public class ActionChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph {

	/** Creates a new instance of ActionChart */
	public ActionChart() {
		super();
		setChannelDisplayX(4);
		setChannelDisplayYArray(new int[] { 1, 3, 2, 5 });
	}

}
