/*
 * AnguloIntensidadeChart.java
 *
 * Created on October 15, 2004, 2:06 PM
 */

package pt.utl.ist.elab.client.usb.cypress.gamma.displays;

/**
 *
 * @author  andre
 */
public class MicChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of AnguloIntensidadeChart */
    public MicChart()
    {
        super();
	setChannelDisplayX(0);
	setChannelDisplayY(2);
    }
    
}
