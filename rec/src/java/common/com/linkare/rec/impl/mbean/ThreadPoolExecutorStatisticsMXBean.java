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

}
