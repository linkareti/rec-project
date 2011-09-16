/* 
 * PinHoleTableModel.java created on 6 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.pinhole;

import com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable;

/**
 * 
 * @author npadriano
 */
public class PinHoleTableModel extends DefaultExperimentDataTable {

	/** Generated UID */
	private static final long serialVersionUID = -6452291751943433228L;
	
	/**
	 * Creates the <code>PinHoleTableModel</code>.
	 */
	public PinHoleTableModel() {
		super();
		
		PinHoleTableModelProxy model = new PinHoleTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}

}
