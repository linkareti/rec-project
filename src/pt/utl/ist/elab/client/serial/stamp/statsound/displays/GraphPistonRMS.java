/*
 * PistonRMS.java
 *
 * Created on October 13, 2003, 5:55 PM
 */

package pt.utl.ist.elab.client.serial.stamp.statsound.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class GraphPistonRMS extends MyDefaultXYExperimentGraph
{    
    /** Creates a new instance of PistonRMS */
    public GraphPistonRMS() 
    {
        super();
        setChannelDisplayX(0);
        setChannelDisplayYArray(new int[]{2,3});
    }    
    
    public String getName()
    {
        return "Chart Piston vs RMS";
    }    
}
