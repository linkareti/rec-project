/*
 * ObjectID.java
 *
 * Created on 8 de Julho de 2003, 13:45
 */

package com.linkare.rec.impl.utils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ObjectID implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1519605890599740771L;
	/** Holds value of property oid. */
	private byte[] oid;

	/** Creates a new instance of ObjectID */
	public ObjectID() {
	}

	/**
	 * Getter for property oid.
	 * 
	 * @return Value of property oid.
	 */
	public byte[] getOid() {
		return oid;
	}

	/**
	 * Setter for property oid.
	 * 
	 * @param oid New value of property oid.
	 */
	public void setOid(final byte[] oid) {
		this.oid = oid;
	}

}
