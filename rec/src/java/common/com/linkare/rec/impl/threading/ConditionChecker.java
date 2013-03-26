/*
 * ConditionChecker.java
 *
 * Created on 11 May 2003, 12:21
 */

package com.linkare.rec.impl.threading;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.linkare.rec.impl.threading.IConditionDecisor.ConditionResult;

/**
 * 
 * @author Jose Pedro Pereira
 */
public final class ConditionChecker implements Runnable {

	/** Holds value of property conditionDecisor. */
	private IConditionDecisor conditionDecisor;

	/** Holds value of property conditionTimeOut. */
	private long conditionTimeOut;

	/** Holds value of property startTime. */
	private long startTime;

	private ScheduledFuture<?> scheduledTask;

	/**
	 * 
	 * Creates the <code>ConditionChecker</code>.
	 * 
	 * @param conditionTimeOut When the condition will absolutely timeout in
	 *            milliseconds
	 * @param conditionCheckInterval How often to check for the condition to be
	 *            met
	 * @param conditionDecisor How to decide if the condition is met
	 */
	public ConditionChecker(final long conditionTimeOut, final long conditionCheckInterval,
			final IConditionDecisor conditionDecisor) {

		startTime = System.currentTimeMillis();
		this.conditionTimeOut = Math.abs(conditionTimeOut);
		long conditionCheckIntervalLocal = Math.min(Math.abs(conditionCheckInterval), this.conditionTimeOut);

		this.conditionDecisor = conditionDecisor;

		scheduledTask = ProcessingManager.getInstance().scheduleAtFixedRate(this, 0, conditionCheckIntervalLocal,
				TimeUnit.MILLISECONDS);
	}

	public void cancelCheck() {
		scheduledTask.cancel(true);
	}

	@Override
	public void run() {

		ConditionResult conditionresult = conditionDecisor.getConditionResult();
		if (conditionresult == ConditionResult.CONDITION_MET_TRUE) {
			conditionDecisor.onConditionMetTrue();
			scheduledTask.cancel(true);
		} else if (conditionresult == ConditionResult.CONDITION_MET_FALSE) {
			conditionDecisor.onConditionMetFalse();
			scheduledTask.cancel(true);
		} else if (conditionresult == ConditionResult.CONDITION_NOT_MET
				&& (conditionTimeOut + startTime) < System.currentTimeMillis()) {
			conditionDecisor.onConditionTimeOut();
			scheduledTask.cancel(false);
		}

	}

}
