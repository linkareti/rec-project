package com.linkare.rec.am.aop;

import static com.linkare.rec.am.util.LoggerSingleton.LOGGER;

import java.io.Serializable;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LoggerExceptionHandler implements Serializable {

    private static final long serialVersionUID = -4481196900668275898L;

    public Object execute(final Object target, final Object methodResult, final Throwable throwable) {
	LOGGER.info("Logging the exception " + throwable.getStackTrace());
	return methodResult;
    }
}