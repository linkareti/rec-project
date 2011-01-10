/* 
 * CSVMultSeriesTableModelProxy.java created on 17 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment.export.csv;

import com.linkare.rec.impl.client.experiment.MultSeriesTableModelProxy;

/**
 * 
 * @author npadriano
 */
public class CSVMultSeriesTableModelProxy implements CSVModel {

	private MultSeriesTableModelProxy model;

	/**
	 * Creates the <code>CSVMultSeriesTableModelProxy</code>.
	 */
	public CSVMultSeriesTableModelProxy(MultSeriesTableModelProxy model) {
		this.model = model;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCell(int row, int col) {
		return model.getValueAt(row, col).toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getColumnCount() {
		return model.getColumnCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getColumnHeader(int index) {
		return model.getColumnName(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getRowCount() {
		return model.getRowCount();
	}

}
