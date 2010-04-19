/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.virtual.client.colisao.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author Emanuel A.
 */
public class DataTable extends MultSeriesTable {

	/** Creates a new instance of TableFreqRMS */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 });
	}
}
