/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.conducaocalor.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author Andr�
 */
public class Sensor2Table extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8301070185432627000L;

	/** Creates a new instance of TableFreqRMS */
	public Sensor2Table() {
		super();
		setColArray(new int[] { 9, 1, 4, 7 });
	}

	@Override
	public String getName() {
		return "Sensor 2";
	}
}
