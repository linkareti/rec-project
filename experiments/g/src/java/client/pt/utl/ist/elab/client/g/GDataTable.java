/*
 * DefaultExperimentDataTable.java
 *
 * Created on 7 de Maio de 2003, 18:33
 */

package pt.utl.ist.elab.client.g;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GDataTable extends MultSeriesTable {

	/** Creates new form DefaultExperimentDataTable */
	public GDataTable() {
		super();
		
		pt.utl.ist.elab.client.g.GTableModelProxy model = new pt.utl.ist.elab.client.g.GTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}
	
}
