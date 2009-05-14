package com.linkare.rec.data.metadata;

public final class ChannelParameterListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.metadata.ChannelParameter value[] = null;

	public ChannelParameterListHolder()
	{
	}

	public ChannelParameterListHolder(com.linkare.rec.data.metadata.ChannelParameter[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.metadata.ChannelParameterListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.metadata.ChannelParameterListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.metadata.ChannelParameterListHelper.type();
	}

}
