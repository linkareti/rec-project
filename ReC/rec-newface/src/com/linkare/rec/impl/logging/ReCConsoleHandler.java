/* 
 * ReCConsoleHandler.java created on Mar 4, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.logging;

import java.util.logging.ConsoleHandler;

/**
 * This <tt>Handler</tt> publishes log records to <tt>System.out</tt>.
 * 
 * @author Henrique Fernandes
 */
public class ReCConsoleHandler extends ConsoleHandler {

    public ReCConsoleHandler() {
	super();
	setOutputStream(System.out);
    }

}
