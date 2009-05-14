package com.linkare.rec.data.metadata;

public final class ScaleListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.metadata.Scale value[] = null;

	public ScaleListHolder()
	{
	}

	public ScaleListHolder(com.linkare.rec.data.metadata.Scale[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.metadata.ScaleListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.metadata.ScaleListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.metadata.ScaleListHelper.type();
	}

}
