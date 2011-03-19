package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class GraphSoundVel extends SoundVelocityExperimentGraph {

	private static final String CHART_NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.5");

	public GraphSoundVel() {
		super();
		// time - calculated on the proxy of the experiment graph
		setChannelDisplayX(111);
		// wave1 and wave2
		setChannelDisplayYArray(new int[] { 4, 5 });
	}

	@Override
	public String getName() {
		return CHART_NAME;
	}

	@Override
	protected String getChartName(HardwareAcquisitionConfig header) {
		return getName();
	}
}