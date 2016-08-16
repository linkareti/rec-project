/*
 * DataTable.java
 *
 * Created on 20 de Fevereiro de 2005, 22:10
 */

package pt.utl.ist.elab.client.vm3.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author n0dP2
 */
public class DataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6138063550556290417L;

	/** Creates a new instance of DataTable */
	public DataTable() {
		super();
		setColArray(new int[] { 0, 1, 2 });
	}

}
