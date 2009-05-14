package com.linkare.rec.data.metadata;

abstract public class HardwareParameterHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/metadata/HardwareParameter:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.metadata.ChannelParameter that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.metadata.ChannelParameter extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			__typeCode = com.linkare.rec.data.metadata.ChannelParameterHelper.type();
			__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.data.metadata.HardwareParameterHelper.id(), "HardwareParameter", __typeCode);
		}
		return __typeCode;
	}

	public static String id()
	{
		return _id;
	}

	public static com.linkare.rec.data.metadata.ChannelParameter read(org.omg.CORBA.portable.InputStream istream)
	{
		com.linkare.rec.data.metadata.ChannelParameter value = null;
		value = com.linkare.rec.data.metadata.ChannelParameterHelper.read(istream);
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.metadata.ChannelParameter value)
	{
		com.linkare.rec.data.metadata.ChannelParameterHelper.write(ostream, value);
	}

}
