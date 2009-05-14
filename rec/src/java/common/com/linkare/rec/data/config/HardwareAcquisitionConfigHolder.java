package com.linkare.rec.data.config;

public final class HardwareAcquisitionConfigHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.config.HardwareAcquisitionConfig value = null;

	public HardwareAcquisitionConfigHolder()
	{
	}

	public HardwareAcquisitionConfigHolder(com.linkare.rec.data.config.HardwareAcquisitionConfig initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.type();
	}

}
