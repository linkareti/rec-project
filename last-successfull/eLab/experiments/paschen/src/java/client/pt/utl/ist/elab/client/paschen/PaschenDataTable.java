/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.utl.ist.elab.client.paschen;

/**
 *
 * @author jloureiro
 */
public class PaschenDataTable extends com.linkare.rec.impl.ui.table.DefaultExperimentDataTable {

	/**
	 * 
	 */

	/** Creates new form DefaultExperimentDataTable */
	public PaschenDataTable() {
		super();

		final pt.utl.ist.elab.client.paschen.PaschenTableModelProxy model = new pt.utl.ist.elab.client.paschen.PaschenTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}

}
