package com.linkare.rec.data.synch;

import java.util.Calendar;

//Time Structure
public final class Date implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 932566672259876028L;

	//
	// Struct member day
	//
	private byte day;

	//
	// Struct member month
	//
	private byte month;

	//
	// Struct member year
	//
	private short year;

	//
	// Default constructor
	//
	public Date() {
		this(new java.util.GregorianCalendar());

	}

	public Date(final java.util.Calendar calendario) {
		this((byte) calendario.get(Calendar.DAY_OF_MONTH), (byte) calendario.get(Calendar.MONTH), (short) calendario
				.get(Calendar.YEAR));
	}

	//
	// Constructor with fields initialization
	// @param day day struct member
	// @param month month struct member
	// @param year year struct member
	//
	public Date(final byte day, final byte month, final short year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public Date(final Date other) {
		this(other.getDay(), other.getMonth(), other.getYear());
	}

	/**
	 * Getter for property day1.
	 * 
	 * @return Value of property day1.
	 */
	public byte getDay() {
		return day;
	}

	/**
	 * Setter for property day1.
	 * @param day 
	 * 
	 * @param day1 New value of property day1.
	 */
	public void setDay(final byte day) {
		this.day = day;
	}

	/**
	 * Getter for property month.
	 * 
	 * @return Value of property month.
	 */
	public byte getMonth() {
		return month;
	}

	/**
	 * Setter for property month.
	 * 
	 * @param month New value of property month.
	 */
	public void setMonth(final byte month) {
		this.month = month;
	}

	/**
	 * Getter for property year.
	 * 
	 * @return Value of property year.
	 */
	public short getYear() {
		return year;
	}

	/**
	 * Setter for property year.
	 * 
	 * @param year New value of property year.
	 */
	public void setYear(final short year) {
		this.year = year;
	}

	@Override
	public String toString() {
		final java.text.NumberFormat nf4 = java.text.NumberFormat.getNumberInstance();
		nf4.setMaximumFractionDigits(0);
		nf4.setMinimumFractionDigits(0);
		nf4.setMinimumIntegerDigits(4);
		nf4.setMaximumIntegerDigits(4);
		nf4.setGroupingUsed(false);

		final java.text.NumberFormat nf2 = java.text.NumberFormat.getNumberInstance();
		nf2.setMaximumFractionDigits(0);
		nf2.setMinimumFractionDigits(0);
		nf2.setMinimumIntegerDigits(2);
		nf2.setMaximumIntegerDigits(2);

		return nf2.format(day) + "-" + nf2.format(month+1) + "-" + nf4.format(year);
	}
} // class Date
