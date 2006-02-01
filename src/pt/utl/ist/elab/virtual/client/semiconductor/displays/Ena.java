/*
 * Ena.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de E em funcao de na
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author  Pedro Queir� e Nuno Fernandes
 */
public class Ena extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of W */
    public Ena() 
    {
        super();
        setChannelDisplayX(7);
        setChannelDisplayY(3);        
    }    
    
    public String getName()
    {
        return "Campo Electrico em funcao do numero de aceitadores";
    }
}