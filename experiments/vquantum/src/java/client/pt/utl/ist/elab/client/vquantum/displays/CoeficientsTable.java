/*
 * CoeficientsTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.client.vquantum.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author nomead
 */
public class CoeficientsTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5174544680493953879L;

	/** Creates a new instance of CoeficientsTable */
	public CoeficientsTable() {
		super();
		setColArray(new int[] { 9, 2, 3 });
	}
}
