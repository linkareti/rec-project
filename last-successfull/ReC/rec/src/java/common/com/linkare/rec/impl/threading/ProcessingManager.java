package com.linkare.rec.impl.threading;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.linkare.rec.impl.threading.factory.RecThreadFactory;
import com.linkare.rec.impl.utils.Defaults;

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

	// system properties

	public static final String SYSPROP_CORE_POOL_SIZE_MIN_PRIORITY = "ReC.ProcessingManager.ThreadPool.Coresize";
	public static final String SYSPROP_MAX_POOL_SIZE_MIN_PRIORITY = "ReC.ProcessingManager.ThreadPool.Maxsize";
	public static final String SYSPROP_THREAD_IDLE_TIME = "ReC.ProcessingManager.Thread.Idletime";

	private static final int MAX_POOL_SIZE_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_MAX_POOL_SIZE_MIN_PRIORITY), 20);

	private static final int CORE_POOL_SIZE_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_CORE_POOL_SIZE_MIN_PRIORITY), 1);

	private static final int THREAD_IDLE_TIME = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_THREAD_IDLE_TIME), 10);

	private final ThreadPoolExecutor threadPool;

	/**
	 * Singleton instance
	 */
	private final static ProcessingManager instance = new ProcessingManager();

	/**
	 * Singleton constructor private to invalidate creation of instances from
	 * this class.
	 */
	private ProcessingManager() {

		threadPool = new ThreadPoolExecutor(ProcessingManager.CORE_POOL_SIZE_PRIORITY,
				ProcessingManager.MAX_POOL_SIZE_PRIORITY, ProcessingManager.THREAD_IDLE_TIME, TimeUnit.NANOSECONDS,
				new LinkedBlockingQueue<Runnable>(), new RecThreadFactory("EvtThreadPool"));
		threadPool.prestartAllCoreThreads();
	}

	/**
	 * This method enables access to the singleton instance of ProcessingManager
	 * 
	 * @return the single instance of ProcessingManager
	 */

	public static ProcessingManager getInstance() {
		return ProcessingManager.instance;
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
}
