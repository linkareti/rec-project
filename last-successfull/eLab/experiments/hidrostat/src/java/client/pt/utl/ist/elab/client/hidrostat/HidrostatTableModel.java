/* 
 * HidrostatTableModel.java created on 8 Fev 2014
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.hidrostat;

import com.linkare.rec.impl.ui.table.DefaultExperimentDataTable;

/**
 * 
 * @author npadriano
 */
public class HidrostatTableModel extends DefaultExperimentDataTable {

	/** Generated UID */
	private static final long serialVersionUID = -6452291751943433228L;

	/**
	 * Creates the <code>HidrostatTableModel</code>.
	 */
	public HidrostatTableModel() {
		super();

		final HidrostatTableModelProxy model = new HidrostatTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}

}
