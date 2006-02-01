/*
 * Theta1.java
 *
 * Created on 13 February 2004, 20:23
 */

package pt.utl.ist.elab.virtual.client.dpendulum.displays;

/**
 *
 * @author  André
 */
public class W2 extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph 
{
    
    /** Creates a new instance of Theta1 */
    public W2() 
    {
        super();
        setChannelDisplayX(0);
        setChannelDisplayY(4);        
    }    
    
    public String getName()
    {
        return "Gráfico 4";
    }
}
