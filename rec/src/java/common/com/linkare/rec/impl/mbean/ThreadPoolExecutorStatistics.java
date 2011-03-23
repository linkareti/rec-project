/* 
 * ThreadPoolExecutorStatistics.java created on Mar 22, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.mbean;

import com.linkare.rec.impl.threading.ProcessingManager;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class ThreadPoolExecutorStatistics implements ThreadPoolExecutorStatisticsMXBean {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.linkare.rec.impl.threading.ThreadPoolExecutorStatistics getMaxThreadPoolStatistics() {
		return ProcessingManager.getInstance().getMaxThreadPoolStatistics();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.linkare.rec.impl.threading.ThreadPoolExecutorStatistics getMediumThreadPoolStatistics() {
		return ProcessingManager.getInstance().getMediumThreadPoolStatistics();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public com.linkare.rec.impl.threading.ThreadPoolExecutorStatistics getMinThreadPoolStatistics() {
		return ProcessingManager.getInstance().getMinThreadPoolStatistics();
	}

}
