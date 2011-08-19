package com.linkare.rec.data.synch;

import com.linkare.rec.impl.utils.PreciseCalendar;

public final class DateTime implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7664961740758383977L;

	//
	// Struct member date
	//
	private com.linkare.rec.data.synch.Date date;

	//
	// Struct member time
	//
	private com.linkare.rec.data.synch.Time time;

	//
	// Default constructor
	//
	public DateTime() {
		this(new java.util.GregorianCalendar());
	}

	public DateTime(final java.util.Calendar calendario) {
		this(new Date(calendario), new Time(calendario));
	}

	public DateTime(final long millis) {
		final java.util.Calendar c = new java.util.GregorianCalendar();
		c.setTimeInMillis(millis);
		setDate(new Date(c));
		setTime(new Time(c));
	}

	//
	// Constructor with fields initialization
	// @param date date struct member
	// @param time time struct member
	//
	public DateTime(final com.linkare.rec.data.synch.Date date, final com.linkare.rec.data.synch.Time time) {
		this.date = date;
		this.time = time;
	}

	//
	// Copy Constructor
	//
	public DateTime(final DateTime other) {
		setDate(new Date(other.getDate()));
		setTime(new Time(other.getTime()));
	}

	@Override
	public String toString() {
		final PreciseCalendar calendar = new PreciseCalendar(this);
		return calendar.toString();
	}

	public String toSimpleString() {
		String date = "";
		if (this.date != null) {
			date = this.date.toString();
		}
		String time = "";
		if (this.time != null) {
			time = this.time.toSimpleTimeString();
		}
		return date + " " + time;
	}

	public String toSimpleStringTimeFirst() {
		String date = "";
		if (this.date != null) {
			date = this.date.toString();
		}
		String time = "";
		if (this.time != null) {
			time = this.time.toSimpleTimeString();
		}
		return time + " " + date;
	}

	/**
	 * Getter for property date.
	 * 
	 * @return Value of property date.
	 */
	public com.linkare.rec.data.synch.Date getDate() {
		return date;
	}

	/**
	 * Setter for property date.
	 * 
	 * @param date New value of property date.
	 */
	public void setDate(final com.linkare.rec.data.synch.Date date) {
		this.date = date;
	}

	/**
	 * Getter for property time.
	 * 
	 * @return Value of property time.
	 */
	public com.linkare.rec.data.synch.Time getTime() {
		return time;
	}

	/**
	 * Setter for property time.
	 * 
	 * @param time New value of property time.
	 */
	public void setTime(final com.linkare.rec.data.synch.Time time) {
		this.time = time;
	}

	public long getMilliSeconds() {
		final java.util.Calendar c = new java.util.GregorianCalendar(getDate().getYear(), getDate().getMonth(),
				getDate().getDay(), getTime().getHours(), getTime().getMinutes(), getTime().getSeconds());
		c.set(java.util.Calendar.MILLISECOND, getTime().getMilis());
		return c.getTimeInMillis();
	}

	public long getElapsedTimeInMillis() {
		return (System.currentTimeMillis() - getMilliSeconds());
	}

	public long getElapsedTimeInMillis(final DateTime finalDate) {
		return (finalDate.getMilliSeconds() - this.getMilliSeconds());
	}

	public long getElapsedTimeInMicros(final DateTime finalDate) {
		double millis=getElapsedTimeInMillis(finalDate);
		double micros=finalDate.getTime().getMicros()-this.getTime().getMicros();
		return (long)(micros+(millis*1E3));
	}
	
	
	public void addMillis(final long millis) {
		final java.util.Calendar c = new java.util.GregorianCalendar(getDate().getYear(), getDate().getMonth(),
				getDate().getDay(), getTime().getHours(), getTime().getMinutes(), getTime().getSeconds());
		c.setTimeInMillis(getMilliSeconds() + millis);
		setDate(new Date(c));
		setTime(new Time(c));
	}

	public void removeMillis(final long millis) {
		addMillis(millis * -1);
	}

	public DateTime calculateDateTime(final Frequency f, final int sample_index) {
		return PreciseCalendar.calculateDateTime(this, f, sample_index);
	}

} // class DateTime
