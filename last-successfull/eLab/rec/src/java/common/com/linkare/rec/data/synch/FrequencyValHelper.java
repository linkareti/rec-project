package com.linkare.rec.data.synch;

abstract public class FrequencyValHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/FrequencyVal:1.0";

	public static void insert(final org.omg.CORBA.Any a, final double that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(FrequencyValHelper.type());
		FrequencyValHelper.write(out, that);
		a.read_value(out.create_input_stream(), FrequencyValHelper.type());
	}

	public static double extract(final org.omg.CORBA.Any a) {
		return FrequencyValHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (FrequencyValHelper.__typeCode == null) {
			FrequencyValHelper.__typeCode = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_double);
			FrequencyValHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.synch.FrequencyValHelper.id(), "FrequencyVal", FrequencyValHelper.__typeCode);
		}
		return FrequencyValHelper.__typeCode;
	}

	public static String id() {
		return FrequencyValHelper._id;
	}

	public static double read(final org.omg.CORBA.portable.InputStream istream) {
		double value = 0;
		value = istream.read_double();
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final double value) {
		ostream.write_double(value);
	}

}
