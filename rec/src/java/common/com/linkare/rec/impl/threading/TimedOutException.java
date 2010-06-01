/*
 * TimedOutException.java
 *
 * Created on 11 May 2003, 14:07
 */

package com.linkare.rec.impl.threading;

/**
 * 
 * @author Jose Pedro Pereira
 */
public class TimedOutException extends java.lang.Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7306618511566262991L;
	

	/**
	 * Creates a new instance of <code>TimedOutException</code> without detail
	 * message.
	 */
	public TimedOutException() {
	}

	/**
	 * Constructs an instance of <code>TimedOutException</code> with the
	 * specified detail message.
	 * 
	 * @param msg the detail message.
	 */
	public TimedOutException(String msg) {
		super(msg);
	}
}
