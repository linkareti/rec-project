package com.linkare.rec.data.metadata;

abstract public class ParameterNameHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ParameterName:1.0";

	public static void insert(final org.omg.CORBA.Any a, final String that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ParameterNameHelper.type());
		ParameterNameHelper.write(out, that);
		a.read_value(out.create_input_stream(), ParameterNameHelper.type());
	}

	public static String extract(final org.omg.CORBA.Any a) {
		return ParameterNameHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ParameterNameHelper.__typeCode == null) {
			ParameterNameHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
			ParameterNameHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ParameterNameHelper.id(), "ParameterName",
					ParameterNameHelper.__typeCode);
		}
		return ParameterNameHelper.__typeCode;
	}

	public static String id() {
		return ParameterNameHelper._id;
	}

	public static String read(final org.omg.CORBA.portable.InputStream istream) {
		String value = null;
		value = istream.read_wstring();
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final String value) {
		ostream.write_wstring(value);
	}

}
