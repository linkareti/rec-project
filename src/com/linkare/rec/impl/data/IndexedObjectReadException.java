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
public class IndexedObjectReadException extends java.io.IOException
{
    private Object errorObjectKey=null;
    /**
     * Creates a new instance of <code>PacketReadException</code> without detail message.
     */
    public IndexedObjectReadException(java.io.IOException e, Object errorObjectKey)
    {
	super(e.getMessage());
	setStackTrace(e.getStackTrace());
	this.errorObjectKey=errorObjectKey;
    }
    
    
    /** Getter for property errorPacketNumber.
     * @return Value of property errorPacketNumber.
     *
     */
    public Object getErrorObjectKey()
    {
	return errorObjectKey;
    }
    
}
