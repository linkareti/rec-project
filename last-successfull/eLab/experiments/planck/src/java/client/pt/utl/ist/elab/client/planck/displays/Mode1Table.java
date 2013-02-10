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
public class Mode1Table extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3424229319703636220L;

	/** Creates a new instance of TableFreqRMS */
	public Mode1Table() {
		super();
		setColArray(new int[] { 0, 3 });
	}

	@Override
	public String getName() {
		return "Modo Fixo";
	}
}
