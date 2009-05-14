/*
 * VBInd.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de vBI em funcao de nd
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author  Pedro Queir� e Nuno Fernandes
 */
public class VBInd extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of W */
    public VBInd() 
    {
        super();
        setChannelDisplayX(6);
        setChannelDisplayY(2);        
    }    
    
    public String getName()
    {
        return "Potencial Intrinseco em funcao do numero de dadores";
    }
}