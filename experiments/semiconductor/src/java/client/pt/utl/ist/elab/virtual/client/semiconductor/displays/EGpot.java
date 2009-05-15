/*
 * EGpot.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de Eg em funcao de pot
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author  Pedro Queir� e Nuno Fernandes
 */
public class EGpot extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of W */
    public EGpot() 
    {
        super();
        setChannelDisplayX(8);
        setChannelDisplayY(1);        
    }    
    
    public String getName()
    {
        return "Energy Gap em funcao do potencial";
    }
}