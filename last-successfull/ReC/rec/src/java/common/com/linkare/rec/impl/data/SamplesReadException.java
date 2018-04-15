package com.linkare.rec.impl.data;

/**
 * This exception is thrown whenever there is a problem reading data samples
 * from a {@link SamplesSource}
 * 
 * @author José Pedro Pereira
 */
public class SamplesReadException extends java.io.IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8422668385063715056L;
	/**
	 * The number of the sample data index that caused the error
	 */
	private int errorSampleNumber = 0;

	/**
	 * Creates a new instance of <code>PacketReadException</code> without detail
	 * message.
	 * @param e 
	 * @param errorSampleNumber 
	 */
	public SamplesReadException(final java.io.IOException e, final int errorSampleNumber) {
		super(e.getMessage(),e);
		this.errorSampleNumber = errorSampleNumber;
	}

	public SamplesReadException(final String message, final int errorSampleNumber) {
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
