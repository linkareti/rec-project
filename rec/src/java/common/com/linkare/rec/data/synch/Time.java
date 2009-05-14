package com.linkare.rec.data.synch;

public final class Time implements org.omg.CORBA.portable.IDLEntity
{
	//
	// Struct member picos
	//
	private short picos;
	
	//
	// Struct member nanos
	//
	private short nanos;
	
	//
	// Struct member micros
	//
	private short micros;
	
	//
	// Struct member milis
	//
	private short milis;
	
	//
	// Struct member seconds
	//
	private byte seconds;
	
	//
	// Struct member minutes
	//
	private byte minutes;
	
	//
	// Struct member hours
	//
	private byte hours;
	
	public Time()
	{
		this(new java.util.GregorianCalendar());
	}
	
	public Time(java.util.Calendar now)
	{
		this(
		(short)0,
		(short)0,
		(short)0,
		(short)now.get(java.util.Calendar.MILLISECOND),
		(byte)now.get(java.util.Calendar.SECOND),
		(byte)now.get(java.util.Calendar.MINUTE),
		(byte)now.get(java.util.Calendar.HOUR_OF_DAY)
		);
	}
	
	//
	// Constructor with fields initialization
	// @param	picos	picos struct member
	// @param	nanos	nanos struct member
	// @param	micros	micros struct member
	// @param	milis	milis struct member
	// @param	seconds	seconds struct member
	// @param	minutes	minutes struct member
	// @param	hours	hours struct member
	//
	public Time( short picos, short nanos, short micros, short milis, byte seconds, byte minutes, byte hours )
	{
		this.picos = picos;
		this.nanos = nanos;
		this.micros = micros;
		this.milis = milis;
		this.seconds = seconds;
		this.minutes = minutes;
		this.hours = hours;
	}
	
	public Time( Time other )
	{
		this.picos = other.picos;
		this.nanos = other.nanos;
		this.micros = other.micros;
		this.milis = other.milis;
		this.seconds = other.seconds;
		this.minutes = other.minutes;
		this.hours = other.hours;
	}
	
	public String toString()
	{
		java.text.NumberFormat nf3=java.text.NumberFormat.getNumberInstance();
		nf3.setMaximumFractionDigits(0);
		nf3.setMinimumFractionDigits(0);
		nf3.setMinimumIntegerDigits(3);
		nf3.setMaximumIntegerDigits(3);
		
		java.text.NumberFormat nf2=java.text.NumberFormat.getNumberInstance();
		nf2.setMaximumFractionDigits(0);
		nf2.setMinimumFractionDigits(0);
		nf2.setMinimumIntegerDigits(2);
		nf2.setMaximumIntegerDigits(2);
		
		return nf2.format((long)hours)+":"+nf2.format((long)minutes)+":"+nf2.format((long)seconds)+":"+nf3.format((long)milis)+":"+nf3.format((long)micros)+":"+nf3.format((long)nanos)+":"+nf3.format((long)picos);
		
	}
	
	public String toSimpleTimeString()
	{
		java.text.NumberFormat nf3=java.text.NumberFormat.getNumberInstance();
		nf3.setMaximumFractionDigits(0);
		nf3.setMinimumFractionDigits(0);
		nf3.setMinimumIntegerDigits(3);
		nf3.setMaximumIntegerDigits(3);
		
		java.text.NumberFormat nf2=java.text.NumberFormat.getNumberInstance();
		nf2.setMaximumFractionDigits(0);
		nf2.setMinimumFractionDigits(0);
		nf2.setMinimumIntegerDigits(2);
		nf2.setMaximumIntegerDigits(2);
		
		return nf2.format((long)hours)+":"+nf2.format((long)minutes)+":"+nf2.format((long)seconds);
		
	}
	
	/** Getter for property hours.
	 * @return Value of property hours.
	 */
	public byte getHours()
	{
		return this.hours;
	}
	
	/** Setter for property hours.
	 * @param hours New value of property hours.
	 */
	public void setHours(byte hours)
	{
		this.hours = hours;
	}
	
	/** Getter for property minutes.
	 * @return Value of property minutes.
	 */
	public byte getMinutes()
	{
		return this.minutes;
	}
	
	/** Setter for property minutes.
	 * @param minutes New value of property minutes.
	 */
	public void setMinutes(byte minutes)
	{
		this.minutes = minutes;
	}
	
	/** Getter for property seconds.
	 * @return Value of property seconds.
	 */
	public byte getSeconds()
	{
		return this.seconds;
	}
	
	/** Setter for property seconds.
	 * @param seconds New value of property seconds.
	 */
	public void setSeconds(byte seconds)
	{
		this.seconds = seconds;
	}
	
	/** Getter for property milis.
	 * @return Value of property milis.
	 */
	public short getMilis()
	{
		return this.milis;
	}
	
	/** Setter for property milis.
	 * @param milis New value of property milis.
	 */
	public void setMilis(short milis)
	{
		this.milis = milis;
	}
	
	/** Getter for property micros.
	 * @return Value of property micros.
	 */
	public short getMicros()
	{
		return this.micros;
	}
	
	/** Setter for property micros.
	 * @param micros New value of property micros.
	 */
	public void setMicros(short micros)
	{
		this.micros = micros;
	}
	
	/** Getter for property nanos.
	 * @return Value of property nanos.
	 */
	public short getNanos()
	{
		return this.nanos;
	}
	
	/** Setter for property nanos.
	 * @param nanos New value of property nanos.
	 */
	public void setNanos(short nanos)
	{
		this.nanos = nanos;
	}
	
	/** Getter for property picos.
	 * @return Value of property picos.
	 */
	public short getPicos()
	{
		return this.picos;
	}
	
	/** Setter for property picos.
	 * @param picos New value of property picos.
	 */
	public void setPicos(short picos)
	{
		this.picos = picos;
	}
	
	
	
} // class Time
