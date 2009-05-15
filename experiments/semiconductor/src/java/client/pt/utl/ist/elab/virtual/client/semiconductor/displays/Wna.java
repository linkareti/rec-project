/*
 * Wna.java
 *
 * Created on 13 February 2004, 20:23
 *
 * Grafico de W em funcao do numero de aceitadores
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author  Pedro Queir� e Nuno Fernandes
 */
public class Wna extends com.linkare.rec.impl.baseUI.graph.MultSeriesXYExperimentGraph
{
    
    /** Creates a new instance of W */
    public Wna() 
    {
        super();
        setChannelDisplayX(7);
        setChannelDisplayY(0);        
    }    
    
    public String getName()
    {
        return "Largura da zona de Deplecao em funcao do numero de aceitadores";
    }
}