/* 
 * CSVDefaultTableModelProxy.java created on 18 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment.export.csv;

import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author npadriano
 */
public class CSVDefaultTableModelProxy implements CSVModel {
	
	private DefaultTableModel model;
	
	/**
	 * Creates the <code>CSVDefaultTableModelProxy</code>.
	 */
	public CSVDefaultTableModelProxy(DefaultTableModel model) {
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
