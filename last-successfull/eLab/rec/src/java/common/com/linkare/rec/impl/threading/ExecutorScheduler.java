package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorScheduler {

	private static final ScheduledExecutorService scheduler = ExecutorScheduler.newScheduledThreadPool(1);

	public static ScheduledExecutorService newScheduledThreadPool(final int corePoolSize) {
		return new ScheduledThreadPoolExecutor(corePoolSize, new ReCThreadFactory());
	}

	public static ScheduledExecutorService newScheduledThreadPool(final int corePoolSize, final int threadPriority) {
		return new ScheduledThreadPoolExecutor(corePoolSize, new ReCThreadFactory(threadPriority));
	}

	public static void scheduleAtFixedRate(final ScheduledWorkUnit work, final long initialDelay, final long period,
			final TimeUnit unit) {
		final ScheduledFuture<?> shutdownHandler = ExecutorScheduler.scheduler.scheduleWithFixedDelay(
				new ScheduledRunnable(work), initialDelay, period, unit);
		work.setShutdownHandler(shutdownHandler);
	}

	private static class ScheduledRunnable implements Runnable {

		ScheduledWorkUnit work;

		ScheduledRunnable(final ScheduledWorkUnit work) {
			this.work = work;
		}

		@Override
		public void run() {
			try {
				if (work != null) {
					work.run();
				}
			} catch (final Throwable t) {
				work.logThrowable("Throwable caught upon execution of Scheduled Work Unit of type "
						+ work.getClass().getCanonicalName() + ":" + t.getMessage(), t);
			}
		}

	}

	private static class ReCThreadFactory implements ThreadFactory {
		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;
		final int priority;

		ReCThreadFactory() {
			this(Thread.NORM_PRIORITY);
		}

		ReCThreadFactory(final int priority) {
			this.priority = priority;
			final SecurityManager s = System.getSecurityManager();
			group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
			namePrefix = "rec-pool-" + ReCThreadFactory.poolNumber.getAndIncrement() + "-thread-";
		}

		@Override
		public Thread newThread(final Runnable r) {
			final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != priority) {
				t.setPriority(priority);
			}
			return t;
		}
	}

}
