package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ScheduledWorkUnit {

	private ScheduledFuture<?> shutdownHandler = null;

	public abstract void run();

	public void shutdown() {
		shutdownHandler.cancel(true);
	}

	void setShutdownHandler(final ScheduledFuture<?> shutdownHandler) {
		this.shutdownHandler = shutdownHandler;
	}

	/**
	 * 
	 * @param message The message to display in case some exception is caught on
	 *            execution
	 * @param throwable The exception stack trace
	 */
	public void logThrowable(String message, Throwable throwable) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, message, throwable);
	}

}
