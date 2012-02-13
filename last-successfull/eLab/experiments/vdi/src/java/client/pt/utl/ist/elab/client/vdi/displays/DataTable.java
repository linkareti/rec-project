/*
 * Data.java
 *
 * Created on 5 April 2005, 0:53
 */

package pt.utl.ist.elab.client.vdi.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author Queiro'
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5197123341109825425L;

	/** Creates a new instance of Data */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 5, 6, 9, 10 });
	}
}