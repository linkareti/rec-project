/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.momentoinercia.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author Andr�
 */
public class FullTable extends MultSeriesTable {

	/** Creates a new instance of TableFreqRMS */
	public FullTable() {
		super();
		setColArray(new int[] { 2, 0, 1 });
	}

	public String getName() {
		return "Tabela";
	}
}
