package com.linkare.rec.web.util;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LoggerSingleton implements Serializable {

    private static final long serialVersionUID = -6016348244381503949L;
	
	public static final Logger LOGGER = Logger.getLogger("com.linkare.rec.web");

    private LoggerSingleton() {
    }
}