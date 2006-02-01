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
public class PosSigGraph1 extends MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of Sensor1Graph */
    public PosSigGraph1() 
    {
        setChannelDisplayX(0);
        setChannelDisplayY(1);
        setUpdateFrequency(1);
    }
    
    public String getName()
    {
        return "Grafico 1";
    }
}
