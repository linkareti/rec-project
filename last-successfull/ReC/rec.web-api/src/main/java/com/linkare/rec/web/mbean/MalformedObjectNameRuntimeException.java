package com.linkare.rec.web.mbean;

/**
 * To encapsulate the MalformedObjectName as RuntimeException
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */
public class MalformedObjectNameRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 392646758814791625L;

	/**
	 * 
	 */
	public MalformedObjectNameRuntimeException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MalformedObjectNameRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public MalformedObjectNameRuntimeException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public MalformedObjectNameRuntimeException(Throwable cause) {
		super(cause);
	}

}
