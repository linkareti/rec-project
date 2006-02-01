/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.serial.stamp.g;

/**
 *
 * @author  jp
 */
public class HeightTimeXYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of PVXYGraphDisplay */
    public HeightTimeXYGraphDisplay()
    {
	super();
	setChannelDisplayX(1);
	setChannelDisplayY(0);
    }
    
}
