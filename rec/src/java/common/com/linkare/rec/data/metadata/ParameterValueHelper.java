package com.linkare.rec.data.metadata;

abstract public class ParameterValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ParameterValue:1.0";

	public static void insert(final org.omg.CORBA.Any a, final String that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ParameterValueHelper.type());
		ParameterValueHelper.write(out, that);
		a.read_value(out.create_input_stream(), ParameterValueHelper.type());
	}

	public static String extract(final org.omg.CORBA.Any a) {
		return ParameterValueHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ParameterValueHelper.__typeCode == null) {
			ParameterValueHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
			ParameterValueHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ParameterValueHelper.id(), "ParameterValue",
					ParameterValueHelper.__typeCode);
		}
		return ParameterValueHelper.__typeCode;
	}

	public static String id() {
		return ParameterValueHelper._id;
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
