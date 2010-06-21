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
public class GDataTable extends com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable {

	/** Creates new form DefaultExperimentDataTable */
	public GDataTable() {
		super();
		
		pt.utl.ist.elab.client.g.GTableModelProxy model = new pt.utl.ist.elab.client.g.GTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}
	
}
