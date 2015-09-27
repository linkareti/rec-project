package com.linkare.rec.web.aop;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LoggerExceptionHandler implements Serializable {

	private static final Logger LOGGER=Logger.getLogger(LoggerExceptionHandler.class.getPackage().getName());
	
	private static final long serialVersionUID = -4481196900668275898L;

	public Object execute(final Object target, final Object methodResult,
			final Throwable throwable) {
		
		Logger logger = target==null?  LOGGER: Logger.getLogger(target.getClass().getName());

		if (LOGGER.isLoggable(Level.WARNING)) {
			logger.log(Level.WARNING, "Method result [" + methodResult +"]",throwable);
		}

		return methodResult;
	}
}