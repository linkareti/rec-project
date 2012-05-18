/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.planck.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author Andr�
 */
public class Mode0Table extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6716685874320504709L;

	/** Creates a new instance of TableFreqRMS */
	public Mode0Table() {
		super();
		setColArray(new int[] { 1, 2 });
	}

	@Override
	public String getName() {
		return "Modo Varrimento";
	}
}
