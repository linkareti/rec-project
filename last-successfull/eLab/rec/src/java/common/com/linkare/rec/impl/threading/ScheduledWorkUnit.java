package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledFuture;

public abstract class ScheduledWorkUnit {

	private ScheduledFuture<?> shutdownHandler = null;

	public abstract void run();

	public void shutdown() {
		shutdownHandler.cancel(true);
	}

	void setShutdownHandler(ScheduledFuture<?> shutdownHandler) {
		this.shutdownHandler = shutdownHandler;
	}

}
