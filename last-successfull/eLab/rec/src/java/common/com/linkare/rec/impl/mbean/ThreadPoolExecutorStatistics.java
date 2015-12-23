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
	public com.linkare.rec.impl.threading.ThreadPoolExecutorStatistics getThreadPoolStatistics() {
		return ProcessingManager.getInstance().getThreadPoolStatistics();
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setThreadPoolCoreSize(final int corePoolSize) {
		ProcessingManager.getInstance().setThreadPoolCoreSize(corePoolSize);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getThreadPoolCoreSize() {
		return ProcessingManager.getInstance().getThreadPoolCoreSize();
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setThreadPoolMaxSize(final int maxsize) {
		ProcessingManager.getInstance().setThreadPoolMaxSize(maxsize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getThreadPoolMaxSize() {
		return ProcessingManager.getInstance().getThreadPoolMaxSize();
	}


}
