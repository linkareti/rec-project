/* 
 * CSVExpDataModel.java created on 17 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment.export.csv;

import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 * 
 * @author npadriano
 */
public class CSVExpDataModel implements CSVModel {
	
	private ExpDataModel model;
	
	public CSVExpDataModel(ExpDataModel model) {
		this.model = model;
	}

	@Override
	public String getCell(int row, int col) {
		return model.getValueAt(row, col).getValue().toString();
	}

	@Override
	public int getColumnCount() {
		return model.getChannelCount();
	}

	@Override
	public String getColumnHeader(int index) {
		return model.getChannelName(index);
	}

	@Override
	public int getRowCount() {
		return model.getTotalSamples();
	}
	
}