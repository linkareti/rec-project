/*
 * VBIna.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de vBI em funcao de t
 */

package pt.utl.ist.elab.client.vsemiconductor.displays;

/**
 *
 * @author  Pedro Queir� e Nuno Fernandes
 */
public class VBIna extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of W */
    public VBIna() 
    {
        super();
        setChannelDisplayX(7);
        setChannelDisplayY(2);        
    }    
    
    public String getName()
    {
        return "Potencial Intrinseco em funcao do numero de aceitadores";
    }
}