/* 
 * ExpHistoryUINode.java created on 2009/05/15
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.baseUI;

import javax.swing.Icon;

import com.linkare.rec.impl.client.apparatus.Apparatus;
import com.linkare.rec.impl.client.experiment.ExpHistory;
import com.linkare.rec.impl.client.experiment.ExpHistoryDisplayFactory;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * 
 * @author André Leitão
 */
public class ExpHistoryUINode extends ExpHistory {

	private com.linkare.rec.impl.baseUI.config.Apparatus apparatusConfig = null;

	/**
	 * Creates the <code>ExpHistoryUINode</code>.
	 * 
	 * @param expHistoryDisplayFactory
	 * @param producerWrapper
	 * @param apparatus
	 * @param apparatusConfig
	 */
	public ExpHistoryUINode(ExpHistoryDisplayFactory expHistoryDisplayFactory, DataProducerWrapper producerWrapper,
			Apparatus apparatus, com.linkare.rec.impl.baseUI.config.Apparatus apparatusConfig) {
		super(expHistoryDisplayFactory, producerWrapper, apparatus);
		this.apparatusConfig = apparatusConfig;
	}

	public com.linkare.rec.impl.baseUI.config.Apparatus getApparatusConfig() {
		return this.apparatusConfig;
	}

	/**
	 * Getter for property apparatusIcon.
	 * 
	 * @return Value of property apparatusIcon.
	 */
	public Icon getApparatusIcon() {
		return apparatusConfig.getIcon();
	}

}
