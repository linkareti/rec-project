/*
 * B1I1Chart.java
 *
 * Created on April 03, 2005, 3:40 PM
 */

package pt.utl.ist.elab.virtual.client.bs.displays;

/**
 *
 * @author  Queiro'
 */
public class B1I1Chart extends com.linkare.rec.impl.baseUI.graph.DefaultXYExperimentGraph {
    /** Creates a new instance of AnguloIntensidadeChart */
    public B1I1Chart() {
        super();
		setChannelDisplayX(4);
		setChannelDisplayY(2);
    }
}