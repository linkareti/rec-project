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
public class XTimeChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of AnguloIntensidadeChart */
    public XTimeChart()
    {
        super();
	setChannelDisplayX(0);
	setChannelDisplayY(1);
    }
    
}
