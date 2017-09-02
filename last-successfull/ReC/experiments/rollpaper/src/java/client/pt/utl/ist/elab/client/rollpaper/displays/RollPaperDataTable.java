/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.utl.ist.elab.client.rollpaper.displays;

import com.linkare.rec.impl.ui.table.MultSeriesTable;

/**
 * 
 * @author Gedsimon Pereira
 */
public class RollPaperDataTable extends MultSeriesTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3270384894801078396L;

	/** Creates a new instance of RollPaperDataTable */
	public RollPaperDataTable() {
		super();
		setColArray(new int[] { 0, 2, 1, 3, 4 });
	}
}
