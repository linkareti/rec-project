/*
 * CCRawXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.serial.stamp.condensadorCilindrico;

/**
 *
 * @author  jp
 */
public class CCRawXYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of CCRawXYGraphDisplay */
    public CCRawXYGraphDisplay()
    {
	super();
	setChannelDisplayX(2);
	setChannelDisplayY(0);
    }
    
}
