/* 
 * OpticaTableModel.java created on 6 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.wpplanetarium;

import pt.utl.ist.elab.client.wpplanetarium.WPPlanetariumTableModelProxy;

import com.linkare.rec.impl.ui.table.DefaultExperimentDataTable;

/**
 * 
 * @author npadriano
 */
public class WPPlanetariumTableModel extends DefaultExperimentDataTable {

	/** Generated UID */
	private static final long serialVersionUID = -6452291751943433228L;

	/**
	 * Creates the <code>OpticaTableModel</code>.
	 */
	public WPPlanetariumTableModel() {
		super();

		final WPPlanetariumTableModelProxy model = new WPPlanetariumTableModelProxy();
		setActualTableModel(model);
		setExpDataModelContainer(model);
	}

}
