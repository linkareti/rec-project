/*
 * HardwareLockEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:39
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.multicast.DataClientForQueue;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareLockEvent implements Prioritazible {

	/** Holds value of property millisecondsToLockSuccess. */
	private final long millisecondsToLockSuccess;
	private LockCountDown counter = null;
	private DataClientForQueue lockerClient = null;

	/**
	 * Creates a new instance of HardwareLockEvent
	 * 
	 * @param counter
	 * @param millisecondsToLockSuccess
	 * @param lockerClient
	 */
	public HardwareLockEvent(final LockCountDown counter, final long millisecondsToLockSuccess,
			final DataClientForQueue lockerClient) {
		this.counter = counter;
		this.millisecondsToLockSuccess = millisecondsToLockSuccess;
		this.lockerClient = lockerClient;
	}

	/**
	 * Getter for property millisecondsToLockSuccess.
	 * 
	 * @return Value of property millisecondsToLockSuccess.
	 */
	public long getMillisecondsToLockSuccess() {
		return millisecondsToLockSuccess;
	}

	public void startCountDown() {
		counter.startCountDown(millisecondsToLockSuccess);
	}

	public DataClientForQueue getLockerClient() {
		return lockerClient;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MEDIUM;
	}
}
