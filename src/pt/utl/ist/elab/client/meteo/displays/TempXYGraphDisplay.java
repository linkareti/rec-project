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
public class TempXYGraphDisplay extends MeteoTimeExperimentGraph
{
    
    /** Creates a new instance of PVXYGraphDisplay */
    public TempXYGraphDisplay()
    {
	super();
	setChannelTime(0);
	setChannelDisplayY(1);
    }
    
    public String getName()
    {
        return "Temperatura";
    }    
}
