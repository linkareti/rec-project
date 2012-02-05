/*
 * ConditionChecker.java
 *
 * Created on 11 May 2003, 12:21
 */

package com.linkare.rec.impl.threading;

import com.linkare.rec.impl.threading.IConditionDecisor.ConditionResult;

/**
 * 
 * @author Jose Pedro Pereira
 */
public class ConditionChecker {

	/** Holds value of property conditionDecisor. */
	private IConditionDecisor conditionDecisor;

	/** Holds value of property conditionTimeOut. */
	private long conditionTimeOut;

	/** Holds value of property conditionTimeOut. */
	private long conditionCheckInterval;

	/** Holds value of property startTime. */
	private long startTime;

	private final Object synch = new Object();

	private final ConditionCheckerTask checkingTask = new ConditionCheckerTask();

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
		synchronized (synch) {
			startTime = System.currentTimeMillis();
			this.conditionTimeOut = Math.abs(conditionTimeOut);
			this.conditionCheckInterval = Math.abs(conditionCheckInterval);
			if (this.conditionCheckInterval > this.conditionTimeOut) {
				this.conditionCheckInterval = this.conditionTimeOut;
			}

			this.conditionDecisor = conditionDecisor;

			checkingTask.start();

			try {
				synch.wait();
			} catch (final InterruptedException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	public void cancelCheck() {
		checkingTask.cancelCheck();
	}

	private class ConditionCheckerTask extends Thread {
		private boolean cancel = false;
		private boolean stoped = false;
		public Object synchInternal = new Object();

		/**
		 * Creates the <code>ConditionChecker.ConditionCheckerTask</code>.
		 */
		public ConditionCheckerTask() {
			super();
			setName(getName() + " - ConditionCheckerTask");
		}

		@Override
		public void run() {
			synchronized (synch) {
				synch.notifyAll();
			}
			ConditionResult conditionresult = null;
			while (conditionTimeOut + startTime > System.currentTimeMillis() && !isCanceled()) {
				conditionresult = conditionDecisor.getConditionResult();
				if (conditionresult != ConditionResult.CONDITION_NOT_MET) {
					break;
				}

				try {
					final Object o = new Object();
					synchronized (o) {
						o.wait(Math.max(
								0,
								Math.min(conditionCheckInterval,
										startTime + conditionTimeOut - System.currentTimeMillis())));
					}
				} catch (final InterruptedException ignored) {
					return;
				}
			}

			if (isCanceled()) {
				setStoped(true);
				synchronized (synchInternal) {
					synchInternal.notifyAll();
				}
				return;
			}

			if (conditionresult == ConditionResult.CONDITION_MET_TRUE) {
				conditionDecisor.onConditionMetTrue();
			} else if (conditionresult == ConditionResult.CONDITION_MET_FALSE) {
				conditionDecisor.onConditionMetFalse();
			} else if (conditionresult == ConditionResult.CONDITION_NOT_MET) {
				conditionDecisor.onConditionTimeOut();
			}

			setStoped(true);
		}

		private boolean isCanceled() {
			synchronized (synchInternal) {
				return cancel;
			}
		}

		private boolean isStoped() {
			synchronized (synchInternal) {
				return stoped;
			}
		}

		private void setStoped(final boolean stoped) {
			synchronized (synchInternal) {
				this.stoped = stoped;
			}
		}

		public void cancelCheck() {
			if (isStoped()) {
				return;
			}
			synchronized (synchInternal) {
				cancel = true;
				try {
					synchInternal.wait();
				} catch (final InterruptedException ignored) {
				}
			}
		}

	}

}
