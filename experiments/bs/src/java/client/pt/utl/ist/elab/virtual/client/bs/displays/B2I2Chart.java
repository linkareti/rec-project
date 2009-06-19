/*
 * B2I2Chart.java
 *
 * Created on April 03, 2005, 3:40 PM
 */

package pt.utl.ist.elab.virtual.client.bs.displays;

/**
 *
 * @author  Queiro'
 */
public class B2I2Chart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
    /** Creates a new instance of AnguloIntensidadeChart */
    public B2I2Chart() {
        super();
		setChannelDisplayX(5);
		setChannelDisplayY(3);
    }
}