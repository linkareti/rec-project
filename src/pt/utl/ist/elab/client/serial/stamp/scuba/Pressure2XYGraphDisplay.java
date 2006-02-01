/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.serial.stamp.scuba;

/**
 *
 * @author  jp
 */
public class Pressure2XYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of PVXYGraphDisplay */
    public Pressure2XYGraphDisplay()
    {
	super();
	setChannelDisplayX(4);
	setChannelDisplayY(1);
    }
    
}
