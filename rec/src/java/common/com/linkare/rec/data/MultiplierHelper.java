package com.linkare.rec.data;

abstract public class MultiplierHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/Multiplier:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.Multiplier that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.Multiplier extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(com.linkare.rec.data.MultiplierHelper.id(), "Multiplier", new String[]
			{ "fento", "pico", "nano", "micro", "mili", "none", "kilo", "mega", "giga", "tera"} );
		}
		return __typeCode;
	}

	public static String id()
	{
		return _id;
	}

	public static com.linkare.rec.data.Multiplier read(org.omg.CORBA.portable.InputStream istream)
	{
		return new com.linkare.rec.data.Multiplier(istream.read_octet());
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.Multiplier value)
	{
		ostream.write_octet(value.getValue());
	}

}
