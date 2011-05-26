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
	public static final String SYSPROP_CORE_POOL_SIZE_MAX_PRIORITY = "ReC.MultiCast.ProcessingManager.MaxThreadPool.Coresize";
	public static final String SYSPROP_MAX_POOL_SIZE_MAX_PRIORITY = "ReC.MultiCast.ProcessingManager.MaxThreadPool.Maxsize";
	public static final String SYSPROP_CORE_POOL_SIZE_MEDIUM_PRIORITY = "ReC.MultiCast.ProcessingManager.MediumThreadPool.Coresize";
	public static final String SYSPROP_MAX_POOL_SIZE_MEDIUM_PRIORITY = "ReC.MultiCast.ProcessingManager.MediumThreadPool.Maxsize";
	public static final String SYSPROP_CORE_POOL_SIZE_MIN_PRIORITY = "ReC.MultiCast.ProcessingManager.MinThreadPool.Coresize";
	public static final String SYSPROP_MAX_POOL_SIZE_MIN_PRIORITY = "ReC.MultiCast.ProcessingManager.MinThreadPool.Maxsize";
	public static final String SYSPROP_THREAD_IDLE_TIME = "DReC.MultiCast.ProcessingManager.Thread.Idletime";

	private static final int MAX_POOL_SIZE_MAX_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_MAX_POOL_SIZE_MAX_PRIORITY), 100);

	private static final int CORE_POOL_SIZE_MAX_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_CORE_POOL_SIZE_MAX_PRIORITY), 50);

	private static final int MAX_POOL_SIZE_MEDIUM_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_MAX_POOL_SIZE_MEDIUM_PRIORITY), 20);

	private static final int CORE_POOL_SIZE_MEDIUM_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_CORE_POOL_SIZE_MEDIUM_PRIORITY), 10);

	private static final int MAX_POOL_SIZE_MIN_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_MAX_POOL_SIZE_MIN_PRIORITY), 10);

	private static final int CORE_POOL_SIZE_MIN_PRIORITY = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_CORE_POOL_SIZE_MIN_PRIORITY), 1);

	private static final int THREAD_IDLE_TIME = Defaults.defaultIfEmpty(
			System.getProperty(ProcessingManager.SYSPROP_THREAD_IDLE_TIME), 10);

	private static final String MAX_THREADPOOL_PREFIX = "MaxThreadPool";
	private static final String MEDIUM_THREADPOOL_PREFIX = "MediumThreadPool";
	private static final String MIN_THREADPOOL_PREFIX = "MinThreadPool";

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

		threadPoolMaxPriority = new ThreadPoolExecutor(ProcessingManager.CORE_POOL_SIZE_MAX_PRIORITY,
				ProcessingManager.MAX_POOL_SIZE_MAX_PRIORITY, ProcessingManager.THREAD_IDLE_TIME, TimeUnit.NANOSECONDS,
				new LinkedBlockingQueue<Runnable>(), new RecThreadFactory(ProcessingManager.MAX_THREADPOOL_PREFIX));
		threadPoolMaxPriority.prestartAllCoreThreads();

		threadPoolMediumPriority = new ThreadPoolExecutor(ProcessingManager.CORE_POOL_SIZE_MEDIUM_PRIORITY,
				ProcessingManager.MAX_POOL_SIZE_MEDIUM_PRIORITY, ProcessingManager.THREAD_IDLE_TIME,
				TimeUnit.NANOSECONDS, new LinkedBlockingQueue<Runnable>(), new RecThreadFactory(
						ProcessingManager.MEDIUM_THREADPOOL_PREFIX));
		threadPoolMediumPriority.prestartAllCoreThreads();

		threadPoolMinPriority = new ThreadPoolExecutor(ProcessingManager.CORE_POOL_SIZE_MIN_PRIORITY,
				ProcessingManager.MAX_POOL_SIZE_MIN_PRIORITY, ProcessingManager.THREAD_IDLE_TIME, TimeUnit.NANOSECONDS,
				new LinkedBlockingQueue<Runnable>(), new RecThreadFactory(ProcessingManager.MIN_THREADPOOL_PREFIX));
		threadPoolMinPriority.prestartAllCoreThreads();
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
	public void execute(final PriorityRunnable task) {

		switch (task.getPriority()) {
		case MAXIMUM:
			threadPoolMaxPriority.execute(task);
			break;
		case MEDIUM:
			threadPoolMediumPriority.execute(task);
			break;
		case MINIMUM:
			threadPoolMinPriority.execute(task);
			break;
		default:
			throw new IllegalArgumentException("Invalid priority value");
		}
	}

	public Future<?> submit(final PriorityRunnable task) {

		switch (task.getPriority()) {
		case MAXIMUM:
			return threadPoolMaxPriority.submit(task);
		case MEDIUM:
			return threadPoolMediumPriority.submit(task);
		case MINIMUM:
			return threadPoolMinPriority.submit(task);
		default:
			throw new IllegalArgumentException("Invalid priority value");
		}
	}

	public void shutdown() {
		threadPoolMaxPriority.shutdownNow();
		threadPoolMinPriority.shutdownNow();
		threadPoolMediumPriority.shutdownNow();
	}

	public ThreadPoolExecutorStatistics getMediumThreadPoolStatistics() {
		return new ThreadPoolExecutorStatistics(threadPoolMediumPriority.getQueue().size(),
				threadPoolMediumPriority.getActiveCount(), threadPoolMediumPriority.getCompletedTaskCount(),
				threadPoolMediumPriority.getLargestPoolSize());
	}

	public ThreadPoolExecutorStatistics getMinThreadPoolStatistics() {
		return new ThreadPoolExecutorStatistics(threadPoolMinPriority.getQueue().size(),
				threadPoolMinPriority.getActiveCount(), threadPoolMinPriority.getCompletedTaskCount(),
				threadPoolMinPriority.getLargestPoolSize());
	}

	public ThreadPoolExecutorStatistics getMaxThreadPoolStatistics() {
		return new ThreadPoolExecutorStatistics(threadPoolMaxPriority.getQueue().size(),
				threadPoolMaxPriority.getActiveCount(), threadPoolMaxPriority.getCompletedTaskCount(),
				threadPoolMaxPriority.getLargestPoolSize());
	}

	public void setMaxThreadPoolCoreSize(final int corePoolSize) {
		threadPoolMaxPriority.setCorePoolSize(corePoolSize);
	}

	public int getMaxThreadPoolCoreSize() {
		return threadPoolMaxPriority.getCorePoolSize();
	}

	public void setMediumThreadPoolCoreSize(final int corePoolSize) {
		threadPoolMediumPriority.setCorePoolSize(corePoolSize);
	}

	public int getMediumThreadPoolCoreSize() {
		return threadPoolMediumPriority.getCorePoolSize();
	}

	public void setMinThreadPoolCoreSize(final int corePoolSize) {
		threadPoolMinPriority.setCorePoolSize(corePoolSize);
	}

	public int getMinThreadPoolCoreSize() {
		return threadPoolMinPriority.getCorePoolSize();
	}

	public void setMaxThreadPoolMaxSize(final int maxsize) {
		threadPoolMaxPriority.setMaximumPoolSize(maxsize);
	}

	public int getMaxThreadPoolMaxSize() {
		return threadPoolMaxPriority.getMaximumPoolSize();
	}

	public void setMediumThreadPoolMaxSize(final int maxsize) {
		threadPoolMediumPriority.setMaximumPoolSize(maxsize);
	}

	public int getMediumThreadPoolMaxSize() {
		return threadPoolMediumPriority.getMaximumPoolSize();
	}

	public void setMinThreadPoolMaxSize(final int maxsize) {
		threadPoolMinPriority.setMaximumPoolSize(maxsize);
	}

	public int getMinThreadPoolMaxSize() {
		return threadPoolMinPriority.getMaximumPoolSize();
	}

}
