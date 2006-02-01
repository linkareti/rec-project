/*
 * DataReceiverWrapper.java
 *
 * Created on 2 de Abril de 2003, 16:58
 */

package com.linkare.rec.impl.wrappers;

import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.DataReceiverOperations;
import com.linkare.rec.impl.logging.LoggerUtil;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.omg.CORBA.SystemException;

/**
 *
 * @author  jp
 */
public class DataReceiverWrapper implements DataReceiverOperations
{
    
    private DataReceiver delegate=null;
    private boolean connected=false;
    
    private static String DATA_RECEIVER_LOGGER="DataReceiver.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(DATA_RECEIVER_LOGGER));
	}
    }
    
    /** Creates a new instance of DataReceiverWrapper */
    public DataReceiverWrapper(DataReceiver delegate)
    {
	this.delegate=delegate;
	checkConnect();
    }
    
    private void checkConnect()
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.WARNING,"DataReceiver  has not been set! Please set it first...");
	    connected=false;
	}
	try
	{
	    if(delegate._non_existent())
		connected=false;
	    else
		connected=true;
	}catch(Exception e)
	{
	    
	    LoggerUtil.logThrowable("Couldn't determine remote existence of DataReceiver. Assuming disconnected...",e,Logger.getLogger(DATA_RECEIVER_LOGGER));
	    connected=false;
	}
    }
    
    public boolean isConnected()
    {
	checkConnect();
	return connected;
    }
    
    public boolean isSameDelegate(DataReceiverWrapper other)
    {
	return other.delegate._is_equivalent(this.delegate);
    }
    
    public boolean isSameDelegate(DataReceiver other)
    {
	return other._is_equivalent(this.delegate);
    }
    
    public DataReceiver getDelegate()
    {
	return delegate;
    }
    
    public void newSamples(int largestNumPacket)
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.WARNING,"DataReceiver  has not been set! Please set it first...");
	    return;
	}
	try
	{
	    delegate.newSamples(largestNumPacket);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_RECEIVER_LOGGER));
	    checkConnect();
	}
    }
    
    public void stateChanged(DataProducerState newState)
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.WARNING,"DataReceiver  has not been set! Please set it first...");
	    return;
	}
	try
	{
	    delegate.stateChanged(newState);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_RECEIVER_LOGGER));
	    checkConnect();
	}
    }
    
    public void clientsListChanged()
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.WARNING,"DataReceiver  has not been set! Please set it first...");
	    return;
	}
	try
	{
	    delegate.clientsListChanged();
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_RECEIVER_LOGGER));
	    checkConnect();
	}
    }
    
}
