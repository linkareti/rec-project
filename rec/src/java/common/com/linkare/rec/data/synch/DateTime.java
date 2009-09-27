package com.linkare.rec.data.synch;

import com.linkare.rec.impl.utils.PreciseCalendar;

public final class DateTime implements org.omg.CORBA.portable.IDLEntity {
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

	public DateTime(java.util.Calendar calendario) {
		this(new Date(calendario), new Time(calendario));
	}

	public DateTime(long millis) {
		java.util.Calendar c = new java.util.GregorianCalendar();
		c.setTimeInMillis(millis);
		this.setDate(new Date(c));
		this.setTime(new Time(c));
	}

	//
	// Constructor with fields initialization
	// @param date date struct member
	// @param time time struct member
	//
	public DateTime(com.linkare.rec.data.synch.Date date, com.linkare.rec.data.synch.Time time) {
		this.date = date;
		this.time = time;
	}

	//
	// Copy Constructor
	//
	public DateTime(DateTime other) {
		this.setDate(new Date(other.getDate()));
		this.setTime(new Time(other.getTime()));
	}

	public String toString() {
		PreciseCalendar calendar = new PreciseCalendar(this);
		return calendar.toString();
	}

	public String toSimpleString() {
		String date = "";
		if (this.date != null)
			date = this.date.toString();
		String time = "";
		if (this.time != null)
			time = this.time.toSimpleTimeString();
		return date + " " + time;
	}

	public String toSimpleStringTimeFirst() {
		String date = "";
		if (this.date != null)
			date = this.date.toString();
		String time = "";
		if (this.time != null)
			time = this.time.toSimpleTimeString();
		return time + " " + date;
	}

	/**
	 * Getter for property date.
	 * 
	 * @return Value of property date.
	 */
	public com.linkare.rec.data.synch.Date getDate() {
		return this.date;
	}

	/**
	 * Setter for property date.
	 * 
	 * @param date New value of property date.
	 */
	public void setDate(com.linkare.rec.data.synch.Date date) {
		this.date = date;
	}

	/**
	 * Getter for property time.
	 * 
	 * @return Value of property time.
	 */
	public com.linkare.rec.data.synch.Time getTime() {
		return this.time;
	}

	/**
	 * Setter for property time.
	 * 
	 * @param time New value of property time.
	 */
	public void setTime(com.linkare.rec.data.synch.Time time) {
		this.time = time;
	}

	public long getMilliSeconds() {
		java.util.Calendar c = new java.util.GregorianCalendar(this.getDate().getYear(), this.getDate().getMonth(),
				this.getDate().getDay(), this.getTime().getHours(), this.getTime().getMinutes(), this.getTime()
						.getSeconds());
		c.set(java.util.Calendar.MILLISECOND, this.getTime().getMilis());
		return c.getTimeInMillis();
	}

	public long getElapsedTimeInMillis() {
		return (System.currentTimeMillis() - getMilliSeconds());
	}

	public long getElapsedTimeInMillis(DateTime other) {
		return (other.getMilliSeconds() - getMilliSeconds());
	}

	public void addMillis(long millis) {
		java.util.Calendar c = new java.util.GregorianCalendar(this.getDate().getYear(), this.getDate().getMonth(),
				this.getDate().getDay(), this.getTime().getHours(), this.getTime().getMinutes(), this.getTime()
						.getSeconds());
		c.setTimeInMillis(getMilliSeconds() + millis);
		this.setDate(new Date(c));
		this.setTime(new Time(c));
	}

	public DateTime calculateDateTime(Frequency f, int sample_index) {
		return PreciseCalendar.calculateDateTime(this, f, sample_index);
	}

} // class DateTime
