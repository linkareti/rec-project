package com.linkare.rec.data.metadata;

public final class VTParameterValueHolder implements org.omg.CORBA.portable.Streamable
{
	public String value;

	public VTParameterValueHolder()
	{
	}

	public VTParameterValueHolder(String initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.metadata.VTParameterValueHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.metadata.VTParameterValueHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.metadata.VTParameterValueHelper.type();
	}

}
