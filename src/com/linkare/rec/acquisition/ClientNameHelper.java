package com.linkare.rec.acquisition;

import java.util.HashMap;

/** CORBA Helper class */
abstract public class ClientNameHelper
{
	private static String  _id = "IDL:com/linkare/rec/acquisition/ClientName:1.0";

	public static void insert(org.omg.CORBA.Any a, String that)
	{
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static String extract(org.omg.CORBA.Any a)
	{
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	synchronized public static org.omg.CORBA.TypeCode type()
	{
		if (__typeCode == null)
		{
			__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
			__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.acquisition.ClientNameHelper.id(), "ClientName", __typeCode);
		}
		return __typeCode;
	}

	public static String id()
	{
		return _id;
	}

	public static String read(org.omg.CORBA.portable.InputStream istream)
	{
		String value = null;
		value = istream.read_wstring();
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, String value)
	{
		ostream.write_wstring(value);
	}

}
