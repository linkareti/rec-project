package com.linkare.rec.data.metadata;

public final class VTFrequencyScaleListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.metadata.FrequencyScale[] value;

	public VTFrequencyScaleListHolder()
	{
	}

	public VTFrequencyScaleListHolder(com.linkare.rec.data.metadata.FrequencyScale[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.type();
	}

}
