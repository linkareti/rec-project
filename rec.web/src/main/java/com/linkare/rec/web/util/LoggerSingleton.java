package com.linkare.rec.web.util;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class LoggerSingleton implements Serializable {

    public static final Logger LOGGER = Logger.getLogger("LoggerSingleton");

    private LoggerSingleton() {
    }
}