/*
 * CondensadorXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.serial.stamp.condensador;

/**
 *
 * @author  jp
 */
public class CondensadorXYGraphDisplay extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph
{
    
    /** Creates a new instance of CondensadorXYGraphDisplay */
    public CondensadorXYGraphDisplay()
    {
	super();
	setChannelDisplayX(0);
	setChannelDisplayY(1);
    }
    
}
