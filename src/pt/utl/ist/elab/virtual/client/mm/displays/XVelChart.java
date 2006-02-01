/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.virtual.client.mm.displays;

/**
 *
 * @author  andre
 */
public class XVelChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of AnguloIntensidadeChart */
    public XVelChart()
    {
        super();
	setChannelDisplayX(1);
	setChannelDisplayY(2);
    }
    
}
