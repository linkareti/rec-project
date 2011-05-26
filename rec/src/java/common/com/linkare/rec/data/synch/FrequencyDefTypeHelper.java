package com.linkare.rec.data.synch;

abstract public class FrequencyDefTypeHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/FrequencyDefType:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.synch.FrequencyDefType that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(FrequencyDefTypeHelper.type());
		FrequencyDefTypeHelper.write(out, that);
		a.read_value(out.create_input_stream(), FrequencyDefTypeHelper.type());
	}

	public static com.linkare.rec.data.synch.FrequencyDefType extract(final org.omg.CORBA.Any a) {
		return FrequencyDefTypeHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (FrequencyDefTypeHelper.__typeCode == null) {
			FrequencyDefTypeHelper.__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.data.synch.FrequencyDefTypeHelper.id(), "FrequencyDefType",
					new String[] { "FrequencyType", "SamplingIntervalType" });
		}
		return FrequencyDefTypeHelper.__typeCode;
	}

	public static String id() {
		return FrequencyDefTypeHelper._id;
	}

	public static com.linkare.rec.data.synch.FrequencyDefType read(final org.omg.CORBA.portable.InputStream istream) {
		return com.linkare.rec.data.synch.FrequencyDefType.from_int(istream.read_long());
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.synch.FrequencyDefType value) {
		ostream.write_long(value.value());
	}

}
