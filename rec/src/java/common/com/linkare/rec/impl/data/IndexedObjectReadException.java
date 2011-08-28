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
public class IndexedObjectReadException extends java.io.IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6776588606049851136L;
	private Object errorObjectKey = null;

	/**
	 * Creates a new instance of <code>PacketReadException</code> without detail
	 * message.
	 * @param e 
	 * @param errorObjectKey 
	 */
	public IndexedObjectReadException(final java.io.IOException e, final Object errorObjectKey) {
		super(e.getMessage());
		setStackTrace(e.getStackTrace());
		this.errorObjectKey = errorObjectKey;
	}

	/**
	 * Getter for property errorPacketNumber.
	 * 
	 * @return Value of property errorPacketNumber.
	 * 
	 */
	public Object getErrorObjectKey() {
		return errorObjectKey;
	}

}
