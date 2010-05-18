/*
 * DataTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.client.vcartpole.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author nomead
 */
public class DataTable extends MultSeriesTable {

	/** Creates a new instance of DataTable */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 2, 1, 3, 4, 5 });
	}
}
