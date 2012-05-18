/*
 * DataTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.client.vcg.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author nomead
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7397023315032117812L;

	/** Creates a new instance of DataTable */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 1, 2 });
	}
}
