package com.linkare.rec.acquisition;

public final class HardwareStateHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.acquisition.HardwareState value = null;

	public HardwareStateHolder()
	{
	}

	public HardwareStateHolder(com.linkare.rec.acquisition.HardwareState initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.acquisition.HardwareStateHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.acquisition.HardwareStateHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.acquisition.HardwareStateHelper.type();
	}

}
