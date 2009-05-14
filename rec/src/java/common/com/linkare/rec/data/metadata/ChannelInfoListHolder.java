package com.linkare.rec.data.metadata;

public final class ChannelInfoListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.metadata.ChannelInfo value[] = null;

	public ChannelInfoListHolder()
	{
	}

	public ChannelInfoListHolder(com.linkare.rec.data.metadata.ChannelInfo[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.metadata.ChannelInfoListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.metadata.ChannelInfoListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.metadata.ChannelInfoListHelper.type();
	}

}
