package pt.utl.ist.elab.client.statsound.displays;

import pt.utl.ist.elab.client.statsound.TypeOfExperiment;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class StatsoundTable extends MultSeriesTable {

	private static final long serialVersionUID = 1821992577555282536L;

	private static final String EXPERIMENT_TYPE = "experiment.type";

	private static final String NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.tip.statsoundTable");

	private TypeOfExperiment typeOfExperiment;

	/**
	 * 
	 * Creates the <code>TableSoundVelocity</code>.
	 * 
	 * It sets the array indexes from which it should fetch the data to be
	 * presented.
	 * 
	 * Notice that the sample number and the acquisition time are calculated on
	 * the model proxy instance.
	 */
	public StatsoundTable() {
		super(new StatsoundTableModelProxy());
	}

	@Override
	public String getName() {
		// TODO: Customise the returned name!
		return StatsoundTable.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setExpDataModel(ExpDataModel model) {
		super.setExpDataModel(model);
		if (model != null) {
			final String experimentTypeParameter = model.getAcquisitionConfig().getSelectedHardwareParameterValue(
					EXPERIMENT_TYPE);
			typeOfExperiment = TypeOfExperiment.from(experimentTypeParameter);

			// Add two dummy values at the beginning of the colArray
			// (SAMPLE_NUMBER_INDEX, ACQUISITION_TIME_INDEX) related to
			// values that do not come directly from the PhysicsValue[] but that
			// are
			// necessary for the presentation of data. Notice that the colArray
			// actually contains the indexes that should be searched from the
			// PhysicsValue[] and, in this case, the sample and acquisition
			// number
			// do not come from it.
			switch (typeOfExperiment) {
			case SOUND_VELOCITY:
				setColArray(new int[] { ChannelConfigConstants.SAMPLE_NUMBER_INDEX,
						ChannelConfigConstants.ACQUISITION_TIME_INDEX, ChannelConfigConstants.WAVE1_INDEX,
						ChannelConfigConstants.WAVE2_INDEX });
				break;
			case STATSOUND_VARY_FREQUENCY:
				setColArray(new int[] { ChannelConfigConstants.SAMPLE_NUMBER_INDEX,
						ChannelConfigConstants.FREQUENCY_INDEX, ChannelConfigConstants.VRMS1_INDEX,
						ChannelConfigConstants.VRMS2_INDEX });
				break;
			case STATSOUND_VARY_PISTON:
				setColArray(new int[] { ChannelConfigConstants.SAMPLE_NUMBER_INDEX,
						ChannelConfigConstants.POSITION_INDEX, ChannelConfigConstants.VRMS1_INDEX,
						ChannelConfigConstants.VRMS2_INDEX });
				break;
			}
		}
	}
}