/*
 * Sensor1Graph.java
 *
 * Created on February 4, 2004, 4:17 PM
 */

package pt.utl.ist.elab.client.serial.stamp.conducaoCalor.displays;

import com.linkare.rec.impl.baseUI.graph.*;
import com.linkare.rec.impl.client.experiment.*;

/**
 *
 * @author  andre
 */
public class Sensor2Graph extends MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of Sensor1Graph */
    public Sensor2Graph() 
    {
        super();
        setChannelDisplayX(9);
        setChannelDisplayYArray(new int[]{1,4,7});
    }
    
    public String getName()
    {
        return "Sensor 2";
    }
}
