package com.linkare.rec.impl.threading;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.linkare.rec.impl.threading.factory.ThreadFactoryMaxPriority;
import com.linkare.rec.impl.threading.factory.ThreadFactoryMediumPriority;
import com.linkare.rec.impl.threading.factory.ThreadFactoryMinPriority;

/**
 * This class is responsible for managing threading pool scheduling and
 * priorities.
 * 
 * @author André Nuno Calado Leitão - Linkare TI
 * @version 0.1
 * 
 */
public final class ProcessingManager {

	// TODO: extract as properties
	private static final int MAX_POOL_SIZE_MAX_PRIORITY = 100;
	private static final int MIN_POOL_SIZE_MAX_PRIORITY = 50;
	private static final int MAX_POOL_SIZE_MEDIUM_PRIORITY = 20;
	private static final int MIN_POOL_SIZE_MEDIUM_PRIORITY = 10;
	private static final int MAX_POOL_SIZE_MIN_PRIORITY = 10;
	private static final int MIN_POOL_SIZE_MIN_PRIORITY = 1;

	private final ThreadPoolExecutor threadPoolMaxPriority;
	private final ThreadPoolExecutor threadPoolMinPriority;
	private final ThreadPoolExecutor threadPoolMediumPriority;

	/**
	 * Singleton instance
	 */
	private final static ProcessingManager instance = new ProcessingManager();

	/**
	 * Singleton constructor private to invalidate creation of instances from
	 * this class.
	 */
	private ProcessingManager() {

		threadPoolMaxPriority = new ThreadPoolExecutor(MIN_POOL_SIZE_MAX_PRIORITY, MAX_POOL_SIZE_MAX_PRIORITY, 10L,
				TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryMaxPriority());
		threadPoolMaxPriority.prestartAllCoreThreads();

		threadPoolMinPriority = new ThreadPoolExecutor(MIN_POOL_SIZE_MEDIUM_PRIORITY, MAX_POOL_SIZE_MEDIUM_PRIORITY,
				10L, TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryMediumPriority());
		threadPoolMinPriority.prestartAllCoreThreads();

		threadPoolMediumPriority = new ThreadPoolExecutor(MIN_POOL_SIZE_MIN_PRIORITY, MAX_POOL_SIZE_MIN_PRIORITY, 10L,
				TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryMinPriority());
		threadPoolMediumPriority.prestartAllCoreThreads();
	}

	/**
	 * This method enables access to the singleton instance of ProcessingManager
	 * 
	 * @return the single instance of ProcessingManager
	 */

	public static ProcessingManager getInstance() {
		return instance;
	}

	/**
	 * Executes the given task sometimes in the future. Divide tasks by
	 * priority.
	 * 
	 * 
	 * @param task to process
	 * @throws NullPointerException if task is null
	 */
	public void execute(PriorityRunnable task) {

		switch (task.getPriority()) {
		case MAXIMUM:
			this.threadPoolMaxPriority.execute(task);
			break;
		case MEDIUM:
			this.threadPoolMediumPriority.execute(task);
			break;
		case MINIMUM:
			this.threadPoolMinPriority.execute(task);
			break;
		default:
			throw new IllegalArgumentException("Invalid priority value");
		}
	}

	public Future<?> submit(PriorityRunnable task) {

		switch (task.getPriority()) {
		case MAXIMUM:
			return this.threadPoolMaxPriority.submit(task);
		case MEDIUM:
			return this.threadPoolMediumPriority.submit(task);
		case MINIMUM:
			return this.threadPoolMinPriority.submit(task);
		default:
			throw new IllegalArgumentException("Invalid priority value");
		}
	}

	public void shutdown() {
		this.threadPoolMaxPriority.shutdownNow();
		this.threadPoolMinPriority.shutdownNow();
		this.threadPoolMediumPriority.shutdownNow();
	}

}
