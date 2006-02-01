/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.serial.stamp.planck.displays;

import com.linkare.rec.impl.baseUI.graph.*;

/**
 *
 * @author  andre
 */
public class Mode0Graph extends MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of Sensor1Graph */
    public Mode0Graph() 
    {
        setChannelDisplayX(1);
        setChannelDisplayY(2);
        setUpdatePercentage(10);
    }
    
    public String getName()
    {
        return "Modo Varrimento";
    }
}
