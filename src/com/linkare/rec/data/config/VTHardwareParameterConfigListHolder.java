package com.linkare.rec.data.config;

public final class VTHardwareParameterConfigListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.config.ParameterConfig[] value;

	public VTHardwareParameterConfigListHolder()
	{
	}

	public VTHardwareParameterConfigListHolder(com.linkare.rec.data.config.ParameterConfig[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.type();
	}

}
