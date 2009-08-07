/*
 * PosVelChart.java
 *
 * Created on 20 de Fevereiro de 2005, 22:13
 */

package pt.utl.ist.elab.client.vm3.displays;

/**
 *
 * @author n0dP2
 */
public class VelPosChart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph{
    
    /** Creates a new instance of PosVelChart */
    public VelPosChart() {
        super();
        setChannelDisplayX(1);
        setChannelDisplayY(2);
    }
    
}
