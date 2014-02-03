/*
 * WaitForConditionTrue.java
 *
 * Created on 11 May 2003, 13:37
 */

package com.linkare.rec.impl.threading;

/**
 * 
 * @author Jose Pedro Pereira
 */
public class WaitForConditionResult extends AbstractConditionDecisor {

	public static void waitForConditionTrue(final IConditionDecisor decisor, final long timeout,
			final long frequencyCheck) throws TimedOutException {
		new WaitForConditionResult(decisor, timeout, frequencyCheck);
	}

	private IConditionDecisor decisorDelegate = null;
	private final Object synch = new Object();
	private volatile boolean throwTimedOut = false;

	/** Creates a new instance of WaitForConditionTrue */
	private WaitForConditionResult(final IConditionDecisor decisorDelegate, final long timeOut,
			final long frequencyCheck) throws TimedOutException {

		this.decisorDelegate = decisorDelegate;

		synchronized (synch) {
			new ConditionChecker(timeOut, frequencyCheck, this);
			try {
				synch.wait(timeOut+100);
			} catch (final InterruptedException ignored) {
			}
		}

		if (throwTimedOut) {
			throw new TimedOutException();
		}
	}

	@Override
	public ConditionResult getConditionResult() {
		return decisorDelegate.getConditionResult();
	}

	@Override
	public void onConditionMetFalse() {
		decisorDelegate.onConditionMetFalse();
		synchronized (synch) {
			throwTimedOut = false;
			synch.notifyAll();
		}
	}

	@Override
	public void onConditionMetTrue() {
		decisorDelegate.onConditionMetTrue();
		synchronized (synch) {
			throwTimedOut = false;
			synch.notifyAll();
		}
	}

	@Override
	public void onConditionTimeOut() {
		decisorDelegate.onConditionTimeOut();
		synchronized (synch) {
			throwTimedOut = true;
			synch.notifyAll();
		}
	}

}
