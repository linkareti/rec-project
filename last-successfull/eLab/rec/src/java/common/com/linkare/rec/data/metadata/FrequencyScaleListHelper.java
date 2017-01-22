package com.linkare.rec.data.metadata;

abstract public class FrequencyScaleListHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/FrequencyScaleList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.FrequencyScale[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(FrequencyScaleListHelper.type());
		FrequencyScaleListHelper.write(out, that);
		a.read_value(out.create_input_stream(), FrequencyScaleListHelper.type());
	}

	public static com.linkare.rec.data.metadata.FrequencyScale[] extract(final org.omg.CORBA.Any a) {
		return FrequencyScaleListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (FrequencyScaleListHelper.__typeCode == null) {
			FrequencyScaleListHelper.__typeCode = com.linkare.rec.data.metadata.FrequencyScaleHelper.type();
			FrequencyScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					FrequencyScaleListHelper.__typeCode);
			FrequencyScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.FrequencyScaleListHelper.id(), "FrequencyScaleList",
					FrequencyScaleListHelper.__typeCode);
		}
		return FrequencyScaleListHelper.__typeCode;
	}

	public static String id() {
		return FrequencyScaleListHelper._id;
	}

	public static com.linkare.rec.data.metadata.FrequencyScale[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.FrequencyScale value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.metadata.FrequencyScale[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.metadata.FrequencyScaleHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.FrequencyScale[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.metadata.FrequencyScaleHelper.write(ostream, value[_i0]);
		}
	}

}