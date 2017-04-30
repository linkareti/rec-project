/* 
 * ICommandTimeoutListener.java created on 27 Jan 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.util.EventListener;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;

/**
 * 
 * @author npadriano
 */
public interface ICommandTimeoutListener extends EventListener {

	public void commandTimeout(SerialPortCommand command);

	public void commandTimeoutNoData();

}
