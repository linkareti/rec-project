package com.linkare.rec.data.config;

abstract public class ParameterConfigListHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/config/ParameterConfigList:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.config.ParameterConfig[] that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.config.ParameterConfig[] extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			__typeCode = com.linkare.rec.data.config.ParameterConfigHelper.type();
			__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0, __typeCode);
			__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.data.config.ParameterConfigListHelper.id(), "ParameterConfigList", __typeCode);
		}
		return __typeCode;
	}

	public static String id()
	{
		return _id;
	}

	public static com.linkare.rec.data.config.ParameterConfig[] read(org.omg.CORBA.portable.InputStream istream)
	{
		com.linkare.rec.data.config.ParameterConfig value[] = null;
		int _len0 = istream.read_long();
		value = new com.linkare.rec.data.config.ParameterConfig[_len0];
		for (int _o1 = 0;_o1 < value.length; ++_o1)
			value[_o1] = com.linkare.rec.data.config.ParameterConfigHelper.read(istream);
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.config.ParameterConfig[] value)
	{
		ostream.write_long(value.length);
		for (int _i0 = 0;_i0 < value.length; ++_i0)
			com.linkare.rec.data.config.ParameterConfigHelper.write(ostream, value[_i0]);
	}

}
