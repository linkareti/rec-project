package pt.utl.ist.elab.client.statsound.displays;

import pt.utl.ist.elab.client.statsound.TypeOfExperiment;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class StatsoundChart extends AbstractStatsoundChart {

	private static final long serialVersionUID = 8368641722434402527L;

	private static final String CHART_NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.tip.statsoundTable");

	private static final String EXPERIMENT_TYPE = "experiment.type";

	private TypeOfExperiment typeOfExperiment;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setExpDataModel(ExpDataModel model) {
		super.setExpDataModel(model);
		final String experimentTypeParameter = getExpDataModel().getAcquisitionConfig()
				.getSelectedHardwareParameterValue(EXPERIMENT_TYPE);
		typeOfExperiment = TypeOfExperiment.from(experimentTypeParameter);
		switch (typeOfExperiment) {
		case SOUND_VELOCITY:
			setChannelDisplayX(ChannelConfigConstants.ACQUISITION_TIME_INDEX);
			setChannelDisplayYArray(new int[] { ChannelConfigConstants.WAVE1_INDEX, ChannelConfigConstants.WAVE2_INDEX });
			break;
		case STATSOUND_VARY_FREQUENCY:
			// TODO: Put here the X that should be presented
			setChannelDisplayX(ChannelConfigConstants.ACQUISITION_TIME_INDEX);
			setChannelDisplayYArray(new int[] { ChannelConfigConstants.VRMS1_INDEX, ChannelConfigConstants.VRMS2_INDEX });
			break;
		case STATSOUND_VARY_PISTON:
			// TODO: Put here the X that should be presented
			setChannelDisplayX(ChannelConfigConstants.ACQUISITION_TIME_INDEX);
			setChannelDisplayYArray(new int[] { ChannelConfigConstants.VRMS1_INDEX, ChannelConfigConstants.VRMS2_INDEX });
			break;
		}
	}
}