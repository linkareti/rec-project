/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.serial.stamp.momentoInercia.displays;

import pt.utl.ist.elab.rec.impl.baseUI.graph.*;
import pt.utl.ist.elab.rec.impl.client.experiment.*;

/**
 *
 * @author  andre
 */
public class PowerGraph extends MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of Sensor1Graph */
    public PowerGraph() 
    {
        setChannelDisplayX(2);
        setChannelDisplayY(1);
        setUpdatePercentage(10);
    }
    
    public String getName()
    {
        return "Potência dissipada";
    }
}
