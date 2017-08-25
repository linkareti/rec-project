/* 
 * CVSModel.java created on 17 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment.export.csv;

/**
 * 
 * @author npadriano
 */
public interface CSVModel {

	/**
	 * Getter for the number of rows.
	 * 
	 * @return
	 */
	public int getRowCount();

	/**
	 * Getter for the number of columns.
	 * 
	 * @return
	 */
	public int getColumnCount();

	/**
	 * Getter for the column name.
	 * 
	 * @param index
	 * @return
	 */
	public String getColumnHeader(int index);

	/**
	 * Getter for the Cell's value.
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public String getCell(int row, int col);
}
