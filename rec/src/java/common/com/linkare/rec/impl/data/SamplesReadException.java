package com.linkare.rec.impl.data;

/**
 * This exception is thrown whenever there is a problem reading data samples 
 * from a {@link SamplesSource}
 *  
 * @author José Pedro Pereira
 */
public class SamplesReadException extends java.io.IOException {
	
	/**
	 * The number of the sample data index that caused the error
	 */
	private int errorSampleNumber = 0;

	/**
	 * Creates a new instance of <code>PacketReadException</code> without detail
	 * message.
	 */
	public SamplesReadException(java.io.IOException e, int errorSampleNumber) {
		super(e.getMessage());
		setStackTrace(e.getStackTrace());
		this.errorSampleNumber = errorSampleNumber;
	}

	public SamplesReadException(String message, int errorSampleNumber) {
		super(message);
		this.errorSampleNumber = errorSampleNumber;
	}

	/**
	 * Getter for property errorSampleNumber.
	 * 
	 * @return Value of property errorSampleNumber.
	 * 
	 */
	public int getErrorSampleNumber() {
		return errorSampleNumber;
	}

}
