/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.vmm.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author Andr�
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758746800495216288L;

	/** Creates a new instance of TableFreqRMS */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 1, 2 });
	}
}
