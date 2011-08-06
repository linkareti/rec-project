package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class GraphSoundVel extends SoundVelocityExperimentGraph {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8368641722434402527L;
	private static final String CHART_NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.5");

	public GraphSoundVel() {
		super();
		setChannelDisplayX(ChannelConfigConstants.ACQUISITION_TIME_INDEX);
		setChannelDisplayYArray(new int[] { ChannelConfigConstants.WAVE1_INDEX, ChannelConfigConstants.WAVE1_INDEX });
	}

	@Override
	public String getName() {
		return GraphSoundVel.CHART_NAME;
	}

	@Override
	protected String getChartName(final HardwareAcquisitionConfig header) {
		return getName();
	}
}