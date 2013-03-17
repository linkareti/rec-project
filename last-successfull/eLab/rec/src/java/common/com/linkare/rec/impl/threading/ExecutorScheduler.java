package com.linkare.rec.impl.threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorScheduler {

	public static void scheduleAtFixedRate(final ScheduledWorkUnit work, final long initialDelay, final long period,
			final TimeUnit unit) {
		final ScheduledFuture<?> shutdownHandler = ProcessingManager.getInstance().scheduleAtFixedRate(
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
			} catch (final Exception e) {
				work.logThrowable("Throwable caught upon execution of Scheduled Work Unit of type "
						+ work.getClass().getCanonicalName() + ":" + e.getMessage(), e);
			}
		}

	}

}
