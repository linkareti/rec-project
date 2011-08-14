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
	 */
	public SamplesPacketReadException(final java.io.IOException e, final int errorPacketNumber) {
		super(e.getMessage());
		setStackTrace(e.getStackTrace());
		this.errorPacketNumber = errorPacketNumber;
	}

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
