/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.conducaocalor.displays;

import com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class Sensor1Graph extends MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of Sensor1Graph */
    public Sensor1Graph() 
    {
        super();
        setChannelDisplayX(9);
        setChannelDisplayYArray(new int[]{0,3,6});
    }
    
    public String getName()
    {
        return "Sensor 1";
    }
}
