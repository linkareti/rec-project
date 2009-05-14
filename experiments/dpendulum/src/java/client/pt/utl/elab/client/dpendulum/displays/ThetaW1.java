/*
 * Theta1.java
 *
 * Created on 13 February 2004, 20:23
 */

package pt.utl.ist.elab.virtual.client.dpendulum.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class ThetaW1 extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph 
{
    
    /** Creates a new instance of Theta1 */
    public ThetaW1() 
    {
        super();
        setChannelDisplayX(1);
        setChannelDisplayY(3);        
    }    
    
    public String getName()
    {
        return "Grafico 5";
    }
}
