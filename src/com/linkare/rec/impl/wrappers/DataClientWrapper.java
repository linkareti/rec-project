/*
 * DataClientWrapper.java
 *
 * Created on 1 de Abril de 2003, 15:49
 */

package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.logging.LoggerUtil;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class DataClientWrapper implements DataClientOperations
{
    private DataClient delegate=null;
    private boolean connected=false;
    /** Creates a new instance of DataClientWrapper */
    
    private static String DATA_CLIENT_LOGGER="DataClient.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(DATA_CLIENT_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(DATA_CLIENT_LOGGER));
	}
    }
    
    public DataClientWrapper(DataClient delegate)
    {
	this.delegate=delegate;        
	checkConnect();
    }
    
    public boolean isConnected()
    {
	checkConnect();
	return connected;
    }
    
    private void checkConnect()
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_CLIENT_LOGGER).log(Level.WARNING,"DataClient  has not been set! Please set it first...");
	    connected=false;
            return;
	}
	try
	{
            
	    if(delegate._non_existent())
		connected=false;
	    else
		connected=true;
	}catch(Exception e)
	{
	    
	    LoggerUtil.logThrowable("Couldn't determine remote existence of DataClient. Assuming disconnected...",e,Logger.getLogger(DATA_CLIENT_LOGGER));            
	    connected=false;
	}
    }
    
    
    public void hardwareChange()
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_CLIENT_LOGGER).log(Level.WARNING,"DataClient  has not been set! Please set it first...");
	    return;
	}
	
	try
	{
	    delegate.hardwareChange();
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_CLIENT_LOGGER));
	    checkConnect();
	}
	
    }
    
    public void hardwareLockable(long millisecs_to_lock_success)
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_CLIENT_LOGGER).log(Level.WARNING,"DataClient  has not been set! Please set it first...");
	    return;
	}
	
	try
	{
	    delegate.hardwareLockable(millisecs_to_lock_success);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_CLIENT_LOGGER));
	    checkConnect();
	}
	
    }
    
    public void hardwareStateChange(HardwareState newState)
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_CLIENT_LOGGER).log(Level.WARNING,"DataClient  has not been set! Please set it first...");
	    return;
	}
	
	try
	{
	    delegate.hardwareStateChange(newState);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable("Couldn't call hardwareStateChange on DataClient...",e,Logger.getLogger(DATA_CLIENT_LOGGER));
	    checkConnect();
	}
    }
    
    public void receiveMessage(String clientFrom, String clientTo, String message)
    {
	
	if(delegate==null)
	{
	    Logger.getLogger(DATA_CLIENT_LOGGER).log(Level.WARNING,"DataClient  has not been set! Please set it first...");
	    return;
	}
	
	try
	{
	    delegate.receiveMessage(clientFrom, clientTo, message);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_CLIENT_LOGGER));
	    checkConnect();
	}
    }
    
    public boolean isSameDelegate(DataClientWrapper other)
    {
	return other.delegate._is_equivalent(this.delegate);
    }
    
    public boolean isSameDelegate(DataClient other)
    {
	return other._is_equivalent(this.delegate);
    }
    
    public DataClient getDelegate()
    {
	return delegate;
    }
    
    public UserInfo getUserInfo()
    {
	if(delegate==null)
	{
	    Logger.getLogger(DATA_CLIENT_LOGGER).log(Level.WARNING,"DataClient  has not been set! Please set it first...");
	    return null;
	}
	
	try
	{
	    return delegate.getUserInfo();
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(DATA_CLIENT_LOGGER));
	    checkConnect();
	}
	
	return null;
    }
    
}
