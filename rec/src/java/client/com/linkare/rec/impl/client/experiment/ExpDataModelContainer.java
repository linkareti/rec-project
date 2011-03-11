/* 
 * ExpDataModelContainer.java created on 18 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 * 
 * @author npadriano
 */
public interface ExpDataModelContainer {

	/**
	 * Setter for ExpDataModel
	 * 
	 * @param expDataModel
	 */
	public void setExpDataModel(ExpDataModel expDataModel);

	/**
	 * Getter for ExpDataModel
	 * 
	 * @return returns the expDataModel
	 */
	public ExpDataModel getExpDataModel();
}