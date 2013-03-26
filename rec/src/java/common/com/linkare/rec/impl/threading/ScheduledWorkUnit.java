package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

public abstract class ScheduledWorkUnit {

	private ScheduledFuture<?> shutdownHandler = null;

	protected Logger LOGGER;

	public ScheduledWorkUnit() {
		LOGGER = Logger.getLogger(this.getClass().getName());
	}

	public abstract void run();

	public void shutdown() {
		shutdownHandler.cancel(true);
	}

	void setShutdownHandler(final ScheduledFuture<?> shutdownHandler) {
		this.shutdownHandler = shutdownHandler;
	}

}
