/* 
 * IncorrectRs232ValuesException.java created on 19 Oct 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric;

/**
 * 
 * @author fdias
 */
public class IncorrectRs232ValuesException extends Exception {

	private static final long serialVersionUID = -5169126140740897351L;
	private String message = "";

	/**
	 * Creates the <code>IncorrectRs232ValuesException</code>.
	 * 
	 * @param string
	 */
	public IncorrectRs232ValuesException(String string) {
		this.message = string;
	}

	public String getMessage() {
		return message;
	}

}
