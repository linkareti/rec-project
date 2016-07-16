package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecutorScheduler {
	
	private static final Logger LOGGER=Logger.getLogger(ExecutorScheduler.class.getName());

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
				LOGGER.log(Level.SEVERE,"Throwable caught upon execution of Scheduled Work Unit of type "
						+ work.getClass().getCanonicalName() + ":" + e.getMessage(), e);
			}
		}

	}

}
