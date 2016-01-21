/* 
 * LangmuirDataTable.java created on 27 de Dez de 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.langmuir;

import com.linkare.rec.impl.ui.table.DefaultExperimentDataTable;

/**
 * 
 * @author João Loureiro - IPFN
 */
public class LangmuirDataTable extends DefaultExperimentDataTable {

	/**
	 * Creates the <code>LangmuirDataTable</code>.
	 */
	public LangmuirDataTable() {
		// TODO Auto-generated constructor stub		super();
		super();
		
		final pt.utl.ist.elab.client.langmuir.LangmuirTableModelProxy model = new pt.utl.ist.elab.client.langmuir.LangmuirTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);	}

}
