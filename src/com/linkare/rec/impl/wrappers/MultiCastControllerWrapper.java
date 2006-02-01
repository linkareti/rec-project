/*
 * MultiCastControllerWrapper.java
 *
 * Created on 2 de Abril de 2003, 17:00
 */

package com.linkare.rec.impl.wrappers;

import com.linkare.rec.acquisition.*;
import com.linkare.rec.impl.logging.LoggerUtil;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.omg.CORBA.SystemException;

/**
 *
 * @author  jp
 */
public class MultiCastControllerWrapper implements MultiCastControllerOperations
{
    
    private MultiCastController delegate=null;
    private boolean connected=false;
    private static String MCCONTROLLER_LOGGER="MultiCastController.Logger";
    
    static
    {
	Logger l=LogManager.getLogManager().getLogger(MCCONTROLLER_LOGGER);
	if(l==null)
	{
	    LogManager.getLogManager().addLogger(Logger.getLogger(MCCONTROLLER_LOGGER));
	}
    }
    /** Creates a new instance of MultiCastControllerWrapper */
    public MultiCastControllerWrapper(MultiCastController delegate)
    {
	this.delegate=delegate;
	checkConnect();
    }
    
    private void checkConnect()
    {
	if(delegate==null)
	{
	    Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.WARNING,"MultiCastController has not been set! Please set it first...");
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
	    LoggerUtil.logThrowable("Couldn't determine remote existence of MultiCastController. Assuming disconnected...",e,Logger.getLogger(MCCONTROLLER_LOGGER));
	    connected=false;
	}
    }
    
    public boolean isConnected()
    {
	checkConnect();
	return connected;
    }
    
    
    
    
    
    public boolean isSameDelegate(MultiCastControllerWrapper other)
    {
	return other.delegate._is_equivalent(this.delegate);
    }
    
    public boolean isSameDelegate(MultiCastController other)
    {
	return other._is_equivalent(this.delegate);
    }
    
    
    public MultiCastController getDelegate()
    {
	return delegate;
    }
    
    public MultiCastHardware[] enumerateHardware(UserInfo user) throws NotRegistered, NotAuthorized
    {
	if(delegate==null)
	{
	    Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.WARNING,"MultiCastController has not been set! Please set it first...");
	    return null;
	}
	
	try
	{
	    return delegate.enumerateHardware(user);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(MCCONTROLLER_LOGGER));
	    checkConnect();
	}
	
	return null;
    }
    
    public UserInfo[] getClientList(UserInfo user) throws NotRegistered, NotAuthorized
    {
	if(delegate==null)
	{
	    Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.WARNING,"MultiCastController has not been set! Please set it first...");
	    return null;
	}
	
	try
	{
	    return delegate.getClientList(user);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(MCCONTROLLER_LOGGER));
	    checkConnect();
	}
	
	return null;
    }
    
    public void registerDataClient(DataClient data_client) throws MaximumClientsReached, NotAuthorized
    {
	if(delegate==null)
	{
	    Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.WARNING,"MultiCastController has not been set! Please set it first...");
	    return ;
	}	        
        
	try
	{
	    delegate.registerDataClient(data_client);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(MCCONTROLLER_LOGGER));
	    checkConnect();
	}
    }
    
    public void registerHardware(Hardware hardware)
    {
	if(delegate==null)
	{
	    Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.WARNING,"MultiCastController has not been set! Please set it first...");
	    return ;
	}	
        
	try
	{
	    delegate.registerHardware(hardware);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(MCCONTROLLER_LOGGER));
	    checkConnect();
	}
    }
    
    public void sendMessage(UserInfo user, String clientTo, String message) throws NotRegistered, NotAuthorized
    {
	if(delegate==null)
	{
	    Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.WARNING,"MultiCastController has not been set! Please set it first...");
	    return ;
	}
	
	try
	{
	    delegate.sendMessage(user, clientTo, message);
	}catch(SystemException e)
	{
	    LoggerUtil.logThrowable(null,e,Logger.getLogger(MCCONTROLLER_LOGGER));
	    checkConnect();
	}
    }
}
