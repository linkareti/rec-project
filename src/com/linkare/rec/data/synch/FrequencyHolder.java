package com.linkare.rec.data.synch;

public final class FrequencyHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.synch.Frequency value = null;

	public FrequencyHolder()
	{
	}

	public FrequencyHolder(com.linkare.rec.data.synch.Frequency initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.synch.FrequencyHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.synch.FrequencyHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.synch.FrequencyHelper.type();
	}

}
