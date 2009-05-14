package com.linkare.rec.acquisition;

public final class VTMultiCastHardwareListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.acquisition.MultiCastHardware[] value;

	public VTMultiCastHardwareListHolder()
	{
	}

	public VTMultiCastHardwareListHolder(com.linkare.rec.acquisition.MultiCastHardware[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.type();
	}

}
