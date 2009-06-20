/*
 * ThetaChart.java
 *
 * Created on 1 de Março de 2005, 2:30
 */

package pt.utl.ist.elab.virtual.client.pend2m.displays;

/**
 *
 * @author  Antonio J. R. Figueiredo
 *          Last Review : 6/04/2005
 */
public class ThetaChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
    
    /** Creates a new instance of ThetaChart */
    public ThetaChart(){
        super();
	setChannelDisplayX(0);
	setChannelDisplayY(2);
    }
    
}
