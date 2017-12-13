package com.linkare.rec.data;

abstract public class MultiplierHelper {
	private static String _id = "IDL:com/linkare/rec/data/Multiplier:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.Multiplier that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(MultiplierHelper.type());
		MultiplierHelper.write(out, that);
		a.read_value(out.create_input_stream(), MultiplierHelper.type());
	}

	public static com.linkare.rec.data.Multiplier extract(final org.omg.CORBA.Any a) {
		return MultiplierHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (MultiplierHelper.__typeCode == null) {
			MultiplierHelper.__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.data.MultiplierHelper.id(), "Multiplier",
					new String[] { "fento", "pico", "nano", "micro", "mili", "none", "kilo", "mega", "giga", "tera" });
		}
		return MultiplierHelper.__typeCode;
	}

	public static String id() {
		return MultiplierHelper._id;
	}

	public static com.linkare.rec.data.Multiplier read(final org.omg.CORBA.portable.InputStream istream) {
		return new com.linkare.rec.data.Multiplier(istream.read_octet());
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.Multiplier value) {
		ostream.write_octet(value.getValue());
	}

}
