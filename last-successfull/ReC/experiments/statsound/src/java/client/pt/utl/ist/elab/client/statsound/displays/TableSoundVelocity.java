package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class TableSoundVelocity extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1821992577555282536L;
	private static final String NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.soundVelocityTable");

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
	public TableSoundVelocity() {
		super(new TableSoundVelocityModelProxy());
		// Add two dummy values at the beginning of the colArray
		// (SAMPLE_NUMBER_INDEX, ACQUISITION_TIME_INDEX) related to
		// values that do not come directly from the PhysicsValue[] but that are
		// necessary for the presentation of data. Notice that the colArray
		// actually contains the indexes that should be searched from the
		// PhysicsValue[] and, in this case, the sample and acquisition number
		// do not come from it.
		setColArray(new int[] { ChannelConfigConstants.SAMPLE_NUMBER_INDEX,
				ChannelConfigConstants.ACQUISITION_TIME_INDEX, ChannelConfigConstants.POSITION_INDEX,
				ChannelConfigConstants.VRMS1_INDEX, ChannelConfigConstants.VRMS2_INDEX,
				ChannelConfigConstants.WAVE1_INDEX, ChannelConfigConstants.WAVE2_INDEX,
				ChannelConfigConstants.FREQUENCY_INDEX });
	}

	@Override
	public String getName() {
		return TableSoundVelocity.NAME;
	}
}