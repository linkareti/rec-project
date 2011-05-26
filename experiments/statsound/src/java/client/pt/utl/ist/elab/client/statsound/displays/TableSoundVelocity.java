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
		// sample number, acquisition time, wave1, wave2, piston position,
		// temperature
		setColArray(new int[] { 100, 111, 4, 5, 0, 1 });
	}

	@Override
	public String getName() {
		return TableSoundVelocity.NAME;
	}
}