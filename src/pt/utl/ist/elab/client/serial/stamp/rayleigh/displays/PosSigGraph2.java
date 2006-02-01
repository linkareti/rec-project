/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.serial.stamp.rayleigh.displays;

import pt.utl.ist.elab.rec.impl.baseUI.graph.*;

/**
 *
 * @author  andre
 */
public class PosSigGraph2 extends MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of Sensor1Graph */
    public PosSigGraph2() 
    {
        setChannelDisplayX(0);
        setChannelDisplayY(2);
        setUpdateFrequency(1);
    }
    
    public String getName()
    {
        return "Grafico 2";
    }
}
