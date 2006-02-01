/*
 * ConditionChecker.java
 *
 * Created on 11 May 2003, 12:21
 */

package com.linkare.rec.impl.threading;

import java.util.*;
/**
 *
 * @author  Jose Pedro Pereira
 */
public class ConditionChecker
{
	
	/** Holds value of property conditionDecisor. */
	private IConditionDecisor conditionDecisor;
	
	/** Holds value of property conditionTimeOut. */
	private long conditionTimeOut;
	
	/** Holds value of property conditionTimeOut. */
	private long conditionCheckInterval;
	
	/** Holds value of property startTime. */
	private long startTime;
	
	private Object synch=new Object();
	
	private ConditionCheckerTask checkingTask=new ConditionCheckerTask();
	
	/** Creates a new instance of ConditionChecker */
	public ConditionChecker(long conditionTimeOut,long conditionCheckInterval,IConditionDecisor conditionDecisor)
	{
		synchronized(synch)
		{
			this.startTime=System.currentTimeMillis();
			this.conditionTimeOut=Math.abs(conditionTimeOut);
			this.conditionCheckInterval=Math.abs(conditionCheckInterval);
			if(this.conditionCheckInterval>this.conditionTimeOut)
				this.conditionCheckInterval=this.conditionTimeOut;
			
			this.conditionDecisor=conditionDecisor;
			
			checkingTask.start();
			
			try
			{
				synch.wait();
			}
			catch(InterruptedException e)
			{
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	
	public void cancelCheck()
	{
		checkingTask.cancelCheck();
	}
	
	
	private class ConditionCheckerTask extends Thread
	{
		private boolean cancel=false;
		private boolean stoped=false;
		public Object synchInternal=new Object();
		
		public void run()
		{
			synchronized(synch)
			{
				synch.notifyAll();
			}
			int conditionresult=-1;
			while(conditionTimeOut+startTime>System.currentTimeMillis() && !isCanceled())
			{
				conditionresult=conditionDecisor.getConditionResult();
				if(conditionresult!=IConditionDecisor.CONDITION_NOT_MET)
					break;
					
				try
				{
				    Object o=new Object();
				    synchronized(o)
				    {
					o.wait(Math.max(0,Math.min(conditionCheckInterval,startTime+conditionTimeOut-System.currentTimeMillis())));
				    }
				}catch(InterruptedException ignored)
				{
					return;
				}
			}
			
			if(isCanceled())
			{
				setStoped(true);
				synchronized(synchInternal)
				{
					synchInternal.notifyAll();
				}
				return;
			}
			
			if(conditionresult==IConditionDecisor.CONDITION_MET_TRUE)
				conditionDecisor.onConditionMetTrue();
			else if(conditionresult==IConditionDecisor.CONDITION_MET_FALSE)
				conditionDecisor.onConditionMetFalse();
			else if(conditionresult==IConditionDecisor.CONDITION_NOT_MET)
				conditionDecisor.onConditionTimeOut();
			
			
			setStoped(true);
		}
		
		private boolean isCanceled()
		{
			synchronized(synchInternal)
			{
				return cancel;
			}
		}
		
		private void setCanceled(boolean cancel)
		{
			synchronized(synchInternal)
			{
				this.cancel=cancel;
			}
		}
		
		private boolean isStoped()
		{
			synchronized(synchInternal)
			{
				return stoped;
			}
		}
		
		private void setStoped(boolean stoped)
		{
			synchronized(synchInternal)
			{
				this.stoped=stoped;
			}
		}
		
		public void cancelCheck()
		{
			if(isStoped()) return;
			synchronized(synchInternal)
			{
				cancel=true;
				try
				{
					synchInternal.wait();
				}catch(InterruptedException ignored)
				{}
			}
		}
		
	}
	
	
	
}
