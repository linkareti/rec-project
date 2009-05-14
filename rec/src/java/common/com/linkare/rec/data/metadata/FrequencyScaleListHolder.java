package com.linkare.rec.data.metadata;

public final class FrequencyScaleListHolder implements org.omg.CORBA.portable.Streamable
{
	public com.linkare.rec.data.metadata.FrequencyScale value[] = null;

	public FrequencyScaleListHolder()
	{
	}

	public FrequencyScaleListHolder(com.linkare.rec.data.metadata.FrequencyScale[] initialValue)
	{
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i)
	{
		value = com.linkare.rec.data.metadata.FrequencyScaleListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o)
	{
		com.linkare.rec.data.metadata.FrequencyScaleListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type()
	{
		return com.linkare.rec.data.metadata.FrequencyScaleListHelper.type();
	}

}
