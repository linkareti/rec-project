package com.linkare.rec.data.config;

public final class VTChannelParameterConfigListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.config.ParameterConfig[] value;

	public VTChannelParameterConfigListHolder()
	{
	}

	public VTChannelParameterConfigListHolder(com.linkare.rec.data.config.ParameterConfig[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.config.VTChannelParameterConfigListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.config.VTChannelParameterConfigListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.config.VTChannelParameterConfigListHelper.type();
	}

}
