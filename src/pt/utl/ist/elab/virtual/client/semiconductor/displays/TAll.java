/*
 * EGAll.java
 *
 * Created on June 11, 2004, 12:33 PM
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author  andre
 */


public class TAll extends com.linkare.rec.impl.baseUI.graph.MultiPanelExperimentGraph
{
    
    /** Creates a new instance of EGAll */
    public TAll() 
    {
        super(new Object[]{new EGt(), new Et(), new VBIt(), new Wt(), new Ft()});
    }
    
    public String getName()
    {
        return "Varia\u00e7\u00e3o com T";
    }
}
