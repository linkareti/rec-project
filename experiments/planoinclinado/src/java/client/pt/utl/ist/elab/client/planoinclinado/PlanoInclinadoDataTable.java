/*
 * DefaultExperimentDataTable.java
 *
 * Created on 7 de Maio de 2003, 18:33
 */

package pt.utl.ist.elab.client.planoinclinado;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class PlanoInclinadoDataTable extends com.linkare.rec.impl.ui.table.DefaultExperimentDataTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1885941025221128247L;

	/** Creates new form DefaultExperimentDataTable */
	public PlanoInclinadoDataTable() {
		super();

		final PlanoInclinadoTableModelProxy model = new PlanoInclinadoTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}

}
