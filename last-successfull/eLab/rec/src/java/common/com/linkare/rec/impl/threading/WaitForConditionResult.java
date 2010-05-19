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

	public static void waitForConditionTrue(IConditionDecisor decisor, long timeout, long frequencyCheck)
			throws TimedOutException {
		new WaitForConditionResult(decisor, timeout, frequencyCheck, true);
	}

	public static void waitForConditionTrue(IConditionDecisor decisor, long timeout) throws TimedOutException {
		waitForConditionTrue(decisor, timeout, timeout / 10);
	}

	public static void waitForConditionFalse(IConditionDecisor decisor, long timeout, long frequencyCheck)
			throws TimedOutException {
		new WaitForConditionResult(decisor, timeout, frequencyCheck, false);
	}

	public static void waitForConditionFalse(IConditionDecisor decisor, long timeout) throws TimedOutException {
		waitForConditionFalse(decisor, timeout, timeout / 10);
	}

	private IConditionDecisor decisorDelegate = null;
	private Object synch = new Object();
	private boolean throwTimedOut = false;

	private boolean waitForTrue = false;
	private long timeOut = 0;

	/** Creates a new instance of WaitForConditionTrue */
	private WaitForConditionResult(IConditionDecisor decisorDelegate, long timeOut, long frequencyCheck,
			boolean waitForTrue) throws TimedOutException {

		this.timeOut = timeOut;

		this.waitForTrue = waitForTrue;

		this.decisorDelegate = decisorDelegate;

		synchronized (synch) {
			new ConditionChecker(timeOut, frequencyCheck, this);
			try {
				synch.wait();
			} catch (InterruptedException ignored) {
			}
		}

		if (throwTimedOut)
			throw new TimedOutException();
	}

	public ConditionResult getConditionResult() {
		return decisorDelegate.getConditionResult();
	}

	public void onConditionMetFalse() {
		decisorDelegate.onConditionMetFalse();
		if (!waitForTrue)
			synchronized (synch) {
				throwTimedOut = false;
				synch.notifyAll();
			}
	}

	public void onConditionMetTrue() {
		decisorDelegate.onConditionMetTrue();
		if (waitForTrue)
			synchronized (synch) {
				throwTimedOut = false;
				synch.notifyAll();
			}
	}

	public void onConditionTimeOut() {
		decisorDelegate.onConditionTimeOut();
		synchronized (synch) {
			throwTimedOut = true;
			synch.notifyAll();
		}
	}

}
