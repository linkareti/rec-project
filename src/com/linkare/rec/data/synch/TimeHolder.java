package com.linkare.rec.data.synch;

public final class TimeHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.synch.Time value = null;

	public TimeHolder()
	{
	}

	public TimeHolder(com.linkare.rec.data.synch.Time initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.synch.TimeHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.synch.TimeHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.synch.TimeHelper.type();
	}

}
