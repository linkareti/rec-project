package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class StatsoundChart extends AbstractStatsoundChart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8368641722434402527L;
	private static final String CHART_NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.soundVelocityGraph");

	public StatsoundChart() {
		super();
		setChannelDisplayX(ChannelConfigConstants.ACQUISITION_TIME_INDEX);
		setChannelDisplayYArray(new int[] { ChannelConfigConstants.WAVE1_INDEX, ChannelConfigConstants.WAVE2_INDEX });
	}

	@Override
	public String getName() {
		return StatsoundChart.CHART_NAME;
	}

	@Override
	protected String getChartName(final HardwareAcquisitionConfig header) {
		return getName();
	}
}