/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.statsound.displays;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class TableFreqRMS extends MyDefaultTable {

	private static final String NAME = ReCResourceBundle.findString("statsound$rec.exp.display.statsound.title.1");

	/** Creates a new instance of TableFreqRMS */
	public TableFreqRMS() {
		super();
		setColArray(new int[] { 1, 2, 3 });
	}

	public String getName() {
		return NAME;
	}
}
