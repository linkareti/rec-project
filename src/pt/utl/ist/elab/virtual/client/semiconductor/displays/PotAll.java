/*
 * EGAll.java
 *
 * Created on June 11, 2004, 12:33 PM
 */

package pt.utl.ist.elab.virtual.client.semiconductor.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */


public class PotAll extends com.linkare.rec.impl.baseUI.graph.MultiPanelExperimentGraph
{
    
    /** Creates a new instance of EGAll */
    public PotAll() 
    {
        super(new Object[]{new EGpot(), new Epot(), new VBIpot(), new Wpot()});
    }
    
    public String getName()
    {
        return "Varia\u00e7\u00e3o com Vapp";
    }
}
