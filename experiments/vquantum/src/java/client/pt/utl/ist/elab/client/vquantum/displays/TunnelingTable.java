/*
 * TunnelingTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.client.vquantum.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author nomead
 */
public class TunnelingTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8703468708861005040L;

	/** Creates a new instance of TunnelingTable */
	public TunnelingTable() {
		super();
		setColArray(new int[] { 1, 4, 5, 6 });
	}
}
