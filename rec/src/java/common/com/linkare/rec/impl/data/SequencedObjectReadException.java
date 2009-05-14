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
public class SequencedObjectReadException extends java.io.IOException
{
    private int errorIndex=0;
    /**
     * Creates a new instance of <code>PacketReadException</code> without detail message.
     */
    public SequencedObjectReadException(java.io.IOException e, int errorIndex)
    {
	super(e.getMessage());
	setStackTrace(e.getStackTrace());
	this.errorIndex=errorIndex;
    }
    
    
    /** Getter for property errorPacketNumber.
     * @return Value of property errorPacketNumber.
     *
     */
    public int getErrorIndex()
    {
	return errorIndex;
    }
    
}
