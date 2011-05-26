/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.voscilador.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author RF
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4451878921369340787L;

	/** Creates a new instance of TableFreqRMS */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 1, 2, 3, 4, 13, 14, 15 });
	}
}
