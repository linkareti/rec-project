/*
 * ThetaChart.java
 *
 * Created on 5 de Dezembro de 2004, 2:30
 */

package pt.utl.ist.elab.virtual.client.cartpole.displays;

/**
 *
 * @author  nomead
 */
public class ThetaChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
    
    /** Creates a new instance of ThetaChart */
    public ThetaChart(){
        super();
	setChannelDisplayX(1);
	setChannelDisplayY(3);
    }
    
}
