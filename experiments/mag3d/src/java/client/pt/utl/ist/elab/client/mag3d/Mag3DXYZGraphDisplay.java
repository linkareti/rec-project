/*
 * Mag3DXYZGraphDisplay.java
 *
 * Created on 10 de Julho de 2003, 11:14
 */

package pt.utl.ist.elab.client.mag3d;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.ui.graph.MultSeriesXYExperimentGraph;

/**
 * 
 * @author André Sancho Duarte
 */
public class Mag3DXYZGraphDisplay extends MultSeriesXYExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3177968201292210791L;

	/** Creates a new instance of Mag3DXYZGraphDisplay */
	public Mag3DXYZGraphDisplay() {
		super();
		setChannelDisplayX(0);
		setChannelDisplayYArray(new int[] {4,2,3});
	}

    @Override
    public String getName() {
        return ReCResourceBundle.findStringOrDefault("mag3d$rec.exp.display.mag3d.title.3", "mag3d$rec.exp.display.mag3d.title.3");
    }        
        
}
