package com.linkare.rec.data.metadata;

abstract public class ScaleListHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ScaleList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.Scale[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ScaleListHelper.type());
		ScaleListHelper.write(out, that);
		a.read_value(out.create_input_stream(), ScaleListHelper.type());
	}

	public static com.linkare.rec.data.metadata.Scale[] extract(final org.omg.CORBA.Any a) {
		return ScaleListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ScaleListHelper.__typeCode == null) {
			ScaleListHelper.__typeCode = com.linkare.rec.data.metadata.ScaleHelper.type();
			ScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0, ScaleListHelper.__typeCode);
			ScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ScaleListHelper.id(), "ScaleList", ScaleListHelper.__typeCode);
		}
		return ScaleListHelper.__typeCode;
	}

	public static String id() {
		return ScaleListHelper._id;
	}

	public static com.linkare.rec.data.metadata.Scale[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.Scale value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.metadata.Scale[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.metadata.ScaleHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.Scale[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.metadata.ScaleHelper.write(ostream, value[_i0]);
		}
	}

}
