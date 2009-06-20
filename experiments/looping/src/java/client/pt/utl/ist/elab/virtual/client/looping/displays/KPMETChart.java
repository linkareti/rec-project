/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.virtual.client.looping.displays;

/**
 *
 * @author  Emanuel A.
 */
public class KPMETChart extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of AnguloIntensidadeChart */
    public KPMETChart()
    {
        super();
	setChannelDisplayX(0);
	setChannelDisplayYArray(new int[]{3,4,5});
    }
    
}
