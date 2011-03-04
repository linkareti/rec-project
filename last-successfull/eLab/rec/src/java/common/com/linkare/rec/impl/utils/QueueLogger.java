/* 
 * EventQueueLogger.java created on 5 Aug 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.util.logging.Level;

/**
 * 
 * @author npadriano
 */
public interface QueueLogger {
	
	public void log(Level debugLevel, String message);
	
	public void logThrowable(String message, Throwable t);

}
