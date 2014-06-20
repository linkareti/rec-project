package com.linkare.rec.data.metadata;

abstract public class HardwareParameterListHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/HardwareParameterList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelParameter[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareParameterListHelper.type());
		HardwareParameterListHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareParameterListHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelParameter[] extract(final org.omg.CORBA.Any a) {
		return HardwareParameterListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareParameterListHelper.__typeCode == null) {
			HardwareParameterListHelper.__typeCode = com.linkare.rec.data.metadata.ChannelParameterHelper.type();
			HardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.HardwareParameterHelper.id(), "HardwareParameter",
					HardwareParameterListHelper.__typeCode);
			HardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					HardwareParameterListHelper.__typeCode);
			HardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.HardwareParameterListHelper.id(), "HardwareParameterList",
					HardwareParameterListHelper.__typeCode);
		}
		return HardwareParameterListHelper.__typeCode;
	}

	public static String id() {
		return HardwareParameterListHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelParameter[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.ChannelParameter value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.metadata.ChannelParameter[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.metadata.HardwareParameterHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelParameter[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.metadata.HardwareParameterHelper.write(ostream, value[_i0]);
		}
	}

}
