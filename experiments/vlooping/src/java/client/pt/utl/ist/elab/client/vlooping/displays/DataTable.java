/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.vlooping.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author Emanuel A.
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7044296836646537587L;

	/** Creates a new instance of TableFreqRMS */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 1, 2, 3, 4, 5 });
	}
}
