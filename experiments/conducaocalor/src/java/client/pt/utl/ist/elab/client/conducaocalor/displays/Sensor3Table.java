/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.conducaocalor.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author Andr�
 */
public class Sensor3Table extends MultSeriesTable {

	/** Creates a new instance of TableFreqRMS */
	public Sensor3Table() {
		super();
		setColArray(new int[] { 9, 2, 5, 8 });
	}

	public String getName() {
		return "Sensor 3";
	}
}
