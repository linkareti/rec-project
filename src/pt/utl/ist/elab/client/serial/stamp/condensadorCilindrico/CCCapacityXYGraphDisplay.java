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
public class CCCapacityXYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    private static final long serialVersionUID = -3156779344699977988L;
    
    /** Creates a new instance of CCRawXYGraphDisplay */
    public CCCapacityXYGraphDisplay()
    {
	super();
	setChannelDisplayX(2);
	setChannelDisplayY(1);
    }
    
}
