package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class TableSoundVelocity extends TableSoundVelocityModelProxy {

	private static final String NAME = ReCResourceBundle
			.findString("statsound$rec.exp.display.statsound.title.soundVelocityTable");

	/** Creates a new instance of TablePistonRMS */
	public TableSoundVelocity() {
		super();
		// wave1, wave2, piston position, temperature, sample number, time
		setColArray(new int[] { 3, 4, 0, 1, 2, 6 });
	}

	public String getName() {
		return NAME;
	}
}