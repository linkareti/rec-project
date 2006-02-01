/*
 * HardwareLockEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:39
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.multicast.*;
/**
 *
 * @author  jp
 */
public class HardwareLockEvent
{
	
	/** Holds value of property milliseconds_to_lock_success. */
	private long milliseconds_to_lock_success;
	private LockCountDown counter=null;
	private DataClientForQueue lockerClient=null;
	/** Creates a new instance of HardwareLockEvent */
	public HardwareLockEvent(LockCountDown counter, long milliseconds_to_lock_success, DataClientForQueue lockerClient)
	{
		this.counter=counter;
		this.milliseconds_to_lock_success=milliseconds_to_lock_success;
		this.lockerClient=lockerClient;
	}
	
	/** Getter for property milliseconds_to_lock_success.
	 * @return Value of property milliseconds_to_lock_success.
	 */
	public long getMilliseconds_to_lock_success()
	{
		return this.milliseconds_to_lock_success;
	}
	
	public void startCountDown()
	{
	    counter.startCountDown(milliseconds_to_lock_success);
	}
	
	public DataClientForQueue getLockerClient()
	{
	    return lockerClient;
	}
}
