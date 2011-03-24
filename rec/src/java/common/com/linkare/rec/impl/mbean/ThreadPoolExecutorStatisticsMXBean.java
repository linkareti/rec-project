/* 
 * ThreadPoolExecutorStatisticsMXBean.java created on Mar 22, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.mbean;

import com.linkare.rec.impl.threading.ThreadPoolExecutorStatistics;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public interface ThreadPoolExecutorStatisticsMXBean {

	/**
	 * 
	 * Statistics of MaxPriorityThreadPool
	 * 
	 * @return
	 */
	public ThreadPoolExecutorStatistics getMaxThreadPoolStatistics();

	/**
	 * 
	 * Statistics of MediumPriorityThreadPool
	 * 
	 * @return
	 */
	public ThreadPoolExecutorStatistics getMediumThreadPoolStatistics();

	/**
	 * 
	 * Statistics of MinPriorityThreadPool
	 * 
	 * @return
	 */
	public ThreadPoolExecutorStatistics getMinThreadPoolStatistics();

	public void setMaxThreadPoolCoreSize(final int corePoolSize);

	public int getMaxThreadPoolCoreSize();

	public void setMediumThreadPoolCoreSize(final int corePoolSize);

	public int getMediumThreadPoolCoreSize();

	public void setMinThreadPoolCoreSize(final int corePoolSize);

	public int getMinThreadPoolCoreSize();

	public void setMaxThreadPoolMaxSize(final int maxsize);

	public int getMaxThreadPoolMaxSize();

	public void setMediumThreadPoolMaxSize(final int maxsize);

	public int getMediumThreadPoolMaxSize();

	public void setMinThreadPoolMaxSize(final int maxsize);

	public int getMinThreadPoolMaxSize();

}
