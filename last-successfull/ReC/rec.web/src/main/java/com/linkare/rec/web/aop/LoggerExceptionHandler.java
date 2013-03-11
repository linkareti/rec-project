package com.linkare.rec.web.aop;

import static com.linkare.rec.web.util.LoggerSingleton.LOGGER;

import java.io.Serializable;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LoggerExceptionHandler implements Serializable {

    private static final long serialVersionUID = -4481196900668275898L;

    public Object execute(final Object target, final Object methodResult, final Throwable throwable) {
        LOGGER.log(java.util.logging.Level.WARNING, "Logging the exception with target [" + target + "] methodResult [" + methodResult + "]", throwable);
        return methodResult;
    }
}