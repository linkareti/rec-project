package com.linkare.rec.data.metadata;

public final class VTFrequencyScaleListHelper implements org.omg.CORBA.portable.BoxedValueHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/metadata/VTFrequencyScaleList:1.0";

	private static VTFrequencyScaleListHelper _instance = new VTFrequencyScaleListHelper();


	public VTFrequencyScaleListHelper()
	{
	}

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.metadata.FrequencyScale[] that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.metadata.FrequencyScale[] extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			synchronized (org.omg.CORBA.TypeCode.class)
			{
				if (__typeCode == null)
				{
					if (__active)
					{
						return org.omg.CORBA.ORB.init().create_recursive_tc( _id );
					}
					__active = true;
					__typeCode = com.linkare.rec.data.metadata.FrequencyScaleHelper.type();
					__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0, __typeCode);
					__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.data.metadata.FrequencyScaleListHelper.id(), "FrequencyScaleList", __typeCode);
					__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(_id, "VTFrequencyScaleList", __typeCode);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id()
	{
		return _id;
	}

	public static com.linkare.rec.data.metadata.FrequencyScale[] read(org.omg.CORBA.portable.InputStream istream)
	{
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream))
		{
			throw new org.omg.CORBA.BAD_PARAM(); }
		return (com.linkare.rec.data.metadata.FrequencyScale[]) ((org.omg.CORBA_2_3.portable.InputStream) istream).read_value(_instance);
	}

	public java.io.Serializable read_value(org.omg.CORBA.portable.InputStream istream)
	{
		com.linkare.rec.data.metadata.FrequencyScale[] tmp;
		tmp = com.linkare.rec.data.metadata.FrequencyScaleListHelper.read(istream);
		return (java.io.Serializable) tmp;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.metadata.FrequencyScale[] value)
	{
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream))
		{
			throw new org.omg.CORBA.BAD_PARAM(); }
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, _instance);
	}

	public void write_value(org.omg.CORBA.portable.OutputStream ostream, java.io.Serializable value)
	{
		if (!(value instanceof com.linkare.rec.data.metadata.FrequencyScale[]))
		{
			throw new org.omg.CORBA.MARSHAL(); }
		com.linkare.rec.data.metadata.FrequencyScale[] valueType = (com.linkare.rec.data.metadata.FrequencyScale[]) value;
		com.linkare.rec.data.metadata.FrequencyScaleListHelper.write(ostream, valueType);
	}

	public String get_id()
	{
		return _id;
	}

}
