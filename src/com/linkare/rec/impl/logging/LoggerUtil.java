/*
 * LoggerUtil.java
 *
 * Created on 26 April 2003, 12:40
 */

package com.linkare.rec.impl.logging;

import java.util.logging.*;
import java.io.*;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class LoggerUtil
{
	
	public static void logThrowable(String info_message,Throwable t,Logger logger)
	{
		if(info_message!=null && logger!=null)
		{
		    if(t!=null)
		    {
			StackTraceElement[] trace=t.getStackTrace();
			info_message=" @class " +trace[0].getClassName()+ " ,@method "+trace[0].getMethodName()+" ,@line "+trace[0].getLineNumber()+" " +info_message;
		    }
		    
		    logger.log(Level.WARNING,info_message);
		}
		
		if(t!=null && logger!=null)
		{
			StringWriter sw=new StringWriter();
			t.printStackTrace(new PrintWriter(sw));
			//logger.log(Level.FINE,t.getLocalizedMessage());
			logger.log(Level.FINEST,sw.getBuffer().toString());
		}
	}
	
}
