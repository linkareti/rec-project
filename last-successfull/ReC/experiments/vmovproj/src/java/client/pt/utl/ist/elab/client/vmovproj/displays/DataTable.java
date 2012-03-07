/*
 * DataTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.client.vmovproj.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author nomead
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7020473299452340772L;

	/** Creates a new instance of DataTable */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 3, 7, 1, 4, 8, 2, 5, 9, 6 });
	}
}
