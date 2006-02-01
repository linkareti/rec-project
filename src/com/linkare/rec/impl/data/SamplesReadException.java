/*
 * PacketReadException.java
 *
 * Created on 6 de Janeiro de 2004, 12:09
 */

package com.linkare.rec.impl.data;

/**
 *
 * @author  Administrator
 */
public class SamplesReadException extends java.io.IOException
{
    private int errorSampleNumber=0;
    /**
     * Creates a new instance of <code>PacketReadException</code> without detail message.
     */
    public SamplesReadException(java.io.IOException e, int errorSampleNumber)
    {
	super(e.getMessage());
	setStackTrace(e.getStackTrace());
	this.errorSampleNumber=errorSampleNumber;
    }

    public SamplesReadException(String message, int errorSampleNumber)
    {
	super(message);
	this.errorSampleNumber=errorSampleNumber;
    }
    /** Getter for property errorSampleNumber.
     * @return Value of property errorSampleNumber.
     *
     */
    public int getErrorSampleNumber()
    {
	return errorSampleNumber;
    }
    
  
    
}
