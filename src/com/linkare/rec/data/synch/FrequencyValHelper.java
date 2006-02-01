package com.linkare.rec.data.synch;

abstract public class FrequencyValHelper
{
	private static String  _id = "IDL:com/linkare/rec/data/synch/FrequencyVal:1.0";

	public static void insert(org.omg.CORBA.Any a, double that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static double extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			__typeCode = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_double);
			__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.data.synch.FrequencyValHelper.id(), "FrequencyVal", __typeCode);
		}
		return __typeCode;
	}

	public static String id()
	{
		return _id;
	}

	public static double read(org.omg.CORBA.portable.InputStream istream)
	{
		double value = (double)0;
		value = istream.read_double();
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, double value)
	{
		ostream.write_double(value);
	}

}
