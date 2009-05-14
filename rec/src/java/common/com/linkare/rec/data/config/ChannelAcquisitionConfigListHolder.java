package com.linkare.rec.data.config;

public final class ChannelAcquisitionConfigListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.config.ChannelAcquisitionConfig value[] = null;

	public ChannelAcquisitionConfigListHolder()
	{
	}

	public ChannelAcquisitionConfigListHolder(com.linkare.rec.data.config.ChannelAcquisitionConfig[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.type();
	}

}
