package com.linkare.rec.data.metadata;

abstract public class HardwareParameterHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/HardwareParameter:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelParameter that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareParameterHelper.type());
		HardwareParameterHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareParameterHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelParameter extract(final org.omg.CORBA.Any a) {
		return HardwareParameterHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareParameterHelper.__typeCode == null) {
			HardwareParameterHelper.__typeCode = com.linkare.rec.data.metadata.ChannelParameterHelper.type();
			HardwareParameterHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.HardwareParameterHelper.id(), "HardwareParameter",
					HardwareParameterHelper.__typeCode);
		}
		return HardwareParameterHelper.__typeCode;
	}

	public static String id() {
		return HardwareParameterHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelParameter read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.ChannelParameter value = null;
		value = com.linkare.rec.data.metadata.ChannelParameterHelper.read(istream);
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelParameter value) {
		com.linkare.rec.data.metadata.ChannelParameterHelper.write(ostream, value);
	}

}
