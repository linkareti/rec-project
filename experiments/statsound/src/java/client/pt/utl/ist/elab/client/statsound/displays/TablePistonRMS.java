/*
 * TablePistonRMS.java
 *
 * Created on 16 October 2003, 23:35
 */

package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class TablePistonRMS extends MyDefaultTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -628916417141095768L;
	private static final String NAME = ReCResourceBundle.findString("statsound$rec.exp.display.statsound.title.3");

	/** Creates a new instance of TablePistonRMS */
	public TablePistonRMS() {
		super();
		setColArray(new int[] { 0, 2, 3 });
	}

	@Override
	public String getName() {
		return TablePistonRMS.NAME;
	}
}
