package com.linkare.rec.impl.threading;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.threading.factory.RecThreadFactory;

/**
 * This class is responsible for managing threading pool scheduling and
 * priorities.
 * 
 * @author André Nuno Calado Leitão - Linkare TI
 * @author Artur Correia - Linkare TI
 * 
 * 
 */
public final class ProcessingManager {

	private static final int MAX_POOL_SIZE_PRIORITY = Integer
			.parseInt(ReCSystemProperty.MAX_THREADPOOL_SIZE.getValue());

	private static final int CORE_POOL_SIZE_PRIORITY = Integer.parseInt(ReCSystemProperty.CORE_THREADPOOL_SIZE
			.getValue());

	private static final int THREAD_IDLE_TIME = Integer.parseInt(ReCSystemProperty.MAX_THREADPOOL_IDLETIME.getValue());

	private final ThreadPoolExecutor threadPool;

	private final ScheduledExecutorService scheduleThreadPool;

	/**
	 * Singleton instance
	 */
	private final static ProcessingManager INSTANCE = new ProcessingManager();

	/**
	 * Singleton constructor private to invalidate creation of instances from
	 * this class.
	 */
	private ProcessingManager() {
		scheduleThreadPool = java.util.concurrent.Executors.newSingleThreadScheduledExecutor(new RecThreadFactory(
				"RepeatableTasks"));
		threadPool = new ThreadPoolExecutor(ProcessingManager.CORE_POOL_SIZE_PRIORITY,
				ProcessingManager.MAX_POOL_SIZE_PRIORITY, ProcessingManager.THREAD_IDLE_TIME, TimeUnit.NANOSECONDS,
				new LinkedBlockingQueue<Runnable>(), new RecThreadFactory("EventQueue"));
		threadPool.prestartAllCoreThreads();
	}

	/**
	 * This method enables access to the singleton instance of ProcessingManager
	 * 
	 * @return the single instance of ProcessingManager
	 */

	public static ProcessingManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Executes the given task sometimes in the future. Divide tasks by
	 * priority.
	 * 
	 * 
	 * @param task to process
	 * @throws NullPointerException if task is null
	 */
	public void execute(final Runnable task) {
		threadPool.execute(task);
	}

	public Future<?> submit(final Runnable task) {
		return threadPool.submit(task);
	}

	public void shutdown() {
		threadPool.shutdownNow();
		scheduleThreadPool.shutdownNow();
	}

	public ThreadPoolExecutorStatistics getThreadPoolStatistics() {
		return new ThreadPoolExecutorStatistics(threadPool.getQueue().size(), threadPool.getActiveCount(),
				threadPool.getCompletedTaskCount(), threadPool.getLargestPoolSize());
	}

	public void setThreadPoolCoreSize(final int corePoolSize) {
		threadPool.setCorePoolSize(corePoolSize);
	}

	public int getThreadPoolCoreSize() {
		return threadPool.getCorePoolSize();
	}

	public void setThreadPoolMaxSize(final int maxsize) {
		threadPool.setMaximumPoolSize(maxsize);
	}

	public int getThreadPoolMaxSize() {
		return threadPool.getMaximumPoolSize();
	}

	/**
	 * @param conditionChecker
	 * @param initialDelay
	 * @param period
	 * @param unit
	 * @return
	 */
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable conditionChecker, long initialDelay, long period,
			TimeUnit unit) {
		return scheduleThreadPool.scheduleAtFixedRate(conditionChecker, initialDelay, period, unit);
	}
}
