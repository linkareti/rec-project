/*
 * PVXYGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.meteo.displays;

/**
 *
 * @author  jp
 */
public class PressionXYGraphDisplay extends MeteoTimeExperimentGraph
{
    
    /** Creates a new instance of PVXYGraphDisplay */
    public PressionXYGraphDisplay()
    {
	super();
	setChannelTime(0);
	setChannelDisplayY(8);
    }
    
    public String getName()
    {
        return "Pressao";
    }    
}
