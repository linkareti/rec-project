package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
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
			.findStringOrDefault("statsound$rec.exp.display.statsound.tip.statsoundTable","statsound$rec.exp.display.statsound.tip.statsoundTable");

	private static final String EXPERIMENT_TYPE = "experiment.type";

	private TypeOfExperiment typeOfExperiment;

	public StatsoundChart() {
		super();
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
		if (model != null) {
			final String experimentTypeParameter = getExpDataModel().getAcquisitionConfig()
					.getSelectedHardwareParameterValue(EXPERIMENT_TYPE);
			typeOfExperiment = TypeOfExperiment.from(experimentTypeParameter);
			switch (typeOfExperiment) {
			case SOUND_VELOCITY:
				setChannelDisplayX(ChannelConfigConstants.ACQUISITION_TIME_INDEX);
				setChannelDisplayYArray(new int[] { ChannelConfigConstants.WAVE1_INDEX,
						ChannelConfigConstants.WAVE2_INDEX });
				break;
			case STATSOUND_VARY_FREQUENCY:
				setChannelDisplayX(ChannelConfigConstants.FREQUENCY_INDEX);
				setChannelDisplayYArray(new int[] { ChannelConfigConstants.VRMS1_INDEX,
						ChannelConfigConstants.VRMS2_INDEX });
				break;
			case STATSOUND_VARY_PISTON:
				setChannelDisplayX(ChannelConfigConstants.POSITION_INDEX);
				setChannelDisplayYArray(new int[] { ChannelConfigConstants.VRMS1_INDEX,
						ChannelConfigConstants.VRMS2_INDEX });
				break;
			}
		}
	}
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.CHART;
    }
}