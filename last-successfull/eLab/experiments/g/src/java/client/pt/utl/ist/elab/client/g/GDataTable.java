/*
 * DefaultExperimentDataTable.java
 *
 * Created on 7 de Maio de 2003, 18:33
 */

package pt.utl.ist.elab.client.g;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GDataTable extends com.linkare.rec.impl.ui.table.DefaultExperimentDataTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1885941025221128247L;

	/** Creates new form DefaultExperimentDataTable */
	public GDataTable() {
		super();

		final pt.utl.ist.elab.client.g.GTableModelProxy model = new pt.utl.ist.elab.client.g.GTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}

}
