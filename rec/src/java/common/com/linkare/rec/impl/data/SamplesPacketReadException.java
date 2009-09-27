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
	private int errorPacketNumber = 0;

	/**
	 * Creates a new instance of <code>PacketReadException</code> without detail
	 * message.
	 */
	public SamplesPacketReadException(java.io.IOException e, int errorPacketNumber) {
		super(e.getMessage());
		setStackTrace(e.getStackTrace());
		this.errorPacketNumber = errorPacketNumber;
	}

	public SamplesPacketReadException(String message, int errorPacketNumber) {
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
