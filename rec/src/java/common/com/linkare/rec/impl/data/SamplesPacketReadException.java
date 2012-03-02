/*
 * PacketReadException.java
 *
 * Created on 6 de Janeiro de 2004, 12:09
 */

package com.linkare.rec.impl.data;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SamplesPacketReadException extends java.io.IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1762380206443010873L;
	private int errorPacketNumber = 0;

	/**
	 * Creates a new instance of <code>PacketReadException</code> without detail
	 * message.
	 * 
	 * @param cause
	 * @param errorPacketNumber
	 */
	public SamplesPacketReadException(final Throwable cause, final int errorPacketNumber) {
		super(cause.getMessage(), cause);
		this.errorPacketNumber = errorPacketNumber;
	}

	/**
	 * Creates the <code>SamplesPacketReadException</code>.
	 * @param message
	 * @param cause
	 * @param errorPacketNumber
	 */
	public SamplesPacketReadException(final String message, final Throwable cause, final int errorPacketNumber) {
		super(message, cause);
		this.errorPacketNumber = errorPacketNumber;
	}

	/**
	 * Creates the <code>SamplesPacketReadException</code>.
	 * @param message
	 * @param errorPacketNumber
	 */
	public SamplesPacketReadException(final String message, final int errorPacketNumber) {
		super(message);
		this.errorPacketNumber = errorPacketNumber;
	}

	/**
	 * Getter for property errorSampleNumber.
	 * 
	 * @return Value of property errorSampleNumber.
	 * 
	 */
	public int getErrorPacketNumber() {
		return errorPacketNumber;
	}

}
