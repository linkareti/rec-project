/*
 * Mag3DXYZGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.mag3d;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Mag3DXYZGraphDisplay extends com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177968201292210791L;

	/** Creates a new instance of Mag3DXYZGraphDisplay */
	public Mag3DXYZGraphDisplay() {
		super();
		setChannelDisplayX(0);
                int[] channelList = {3,1,2};
		setChannelDisplayYArray(channelList);
	}

}
