/*
 * Theta1.java
 *
 * Created on 13 February 2004, 20:23
 */

package pt.utl.ist.elab.virtual.client.dpendulum.displays;

/**
 *
 * @author  Andre
 */
public class Theta2 extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph 
{
    
    /** Creates a new instance of Theta1 */
    public Theta2() 
    {
        super();
        setChannelDisplayX(0);
        setChannelDisplayY(2);        
    }    
    
    public String getName()
    {
        return "Grafico 2";
    }
}
