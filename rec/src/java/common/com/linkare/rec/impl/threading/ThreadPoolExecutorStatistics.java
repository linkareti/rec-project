/* 
 * MediumThreadPoolExecutorStatistics.java created on Mar 22, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.threading;

import java.beans.ConstructorProperties;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class ThreadPoolExecutorStatistics {

	private final int numberOfTaskOnQueue;
	private final int corePoolSize;
	private final int maxPoolSize;
	private final int activeCount;
	private final long completedCount;
	private final int largestPoolSize;

	@ConstructorProperties({ "numberOfTaskOnQueue", "corePoolSize", "maxPoolSize", "activeCount", "completedCount",
			"largestPoolSize" })
	public ThreadPoolExecutorStatistics(final int numberOfTaskOnQueue, final int corePoolSize, final int maxPoolSize,
			final int activeCount, final long completedCount, final int largestPoolSize) {
		super();
		this.numberOfTaskOnQueue = numberOfTaskOnQueue;
		this.corePoolSize = corePoolSize;
		this.maxPoolSize = maxPoolSize;
		this.activeCount = activeCount;
		this.completedCount = completedCount;
		this.largestPoolSize = largestPoolSize;
	}

	public int getNumberOfTaskOnQueue() {
		return numberOfTaskOnQueue;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public long getCompletedCount() {
		return completedCount;
	}

	public int getLargestPoolSize() {
		return largestPoolSize;
	}

}
