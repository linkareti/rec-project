/*
 * Et.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de E em funcao de t
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 *
 * @author  Pedro Queir� e Nuno Fernandes
 */
public class Ft extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of W */
    public Ft() 
    {
        super();
        setChannelDisplayX(4);
        setChannelDisplayY(5);        
    }    
    
    public String getName()
    {
        return "Campo Electrico em funcao da temperatura";
    }
}