package com.linkare.rec.repository;

import org.omg.CORBA.portable.ValueBase;

public class VTLong implements ValueBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7025599705236045836L;
	private int value;

	public VTLong() {
		setValue(0);
	}

	public VTLong(final int initial) {
		setValue(initial);
	}

	private static String[] _truncatable_ids = { VTLongHelper.id() };

	@Override
	public String[] _truncatable_ids() {
		return VTLong._truncatable_ids;
	}

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 * 
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 * 
	 */
	public void setValue(final int value) {
		this.value = value;
	}

} // class VTLong
