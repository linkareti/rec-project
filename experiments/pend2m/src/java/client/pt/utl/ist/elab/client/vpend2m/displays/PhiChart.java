/*
 * PhiChart.java
 *
 * Created on 1 de Mar�o de 2005, 2:30
 */

package pt.utl.ist.elab.client.vpend2m.displays;

/**
 *
 * @author  Antonio J. R. Figueiredo
 *          Last Review : 6/04/2005
 */
public class PhiChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
    
    /** Creates a new instance of PhiChart */
    public PhiChart(){
        super();
	setChannelDisplayX(1);
	setChannelDisplayY(3);
    }
    
}
