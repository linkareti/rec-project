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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMaxThreadPoolCoreSize(final int corePoolSize) {
		ProcessingManager.getInstance().setMaxThreadPoolCoreSize(corePoolSize);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxThreadPoolCoreSize() {
		return ProcessingManager.getInstance().getMaxThreadPoolCoreSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMediumThreadPoolCoreSize(final int corePoolSize) {
		ProcessingManager.getInstance().setMaxThreadPoolCoreSize(corePoolSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMediumThreadPoolCoreSize() {
		return ProcessingManager.getInstance().getMediumThreadPoolCoreSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMinThreadPoolCoreSize(final int corePoolSize) {
		ProcessingManager.getInstance().setMinThreadPoolCoreSize(corePoolSize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinThreadPoolCoreSize() {
		return ProcessingManager.getInstance().getMinThreadPoolCoreSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMaxThreadPoolMaxSize(final int maxsize) {
		ProcessingManager.getInstance().setMaxThreadPoolMaxSize(maxsize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxThreadPoolMaxSize() {
		return ProcessingManager.getInstance().getMaxThreadPoolMaxSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMediumThreadPoolMaxSize(final int maxsize) {
		ProcessingManager.getInstance().setMediumThreadPoolMaxSize(maxsize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMediumThreadPoolMaxSize() {
		return ProcessingManager.getInstance().getMediumThreadPoolMaxSize();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMinThreadPoolMaxSize(final int maxsize) {
		ProcessingManager.getInstance().setMinThreadPoolMaxSize(maxsize);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinThreadPoolMaxSize() {
		return ProcessingManager.getInstance().getMinThreadPoolMaxSize();
	}

}
