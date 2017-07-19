package com.linkare.rec.data.config;

abstract public class ChannelAcquisitionConfigListHelper {
	private static String _id = "IDL:com/linkare/rec/data/config/ChannelAcquisitionConfigList:1.0";

	public static void insert(final org.omg.CORBA.Any a,
			final com.linkare.rec.data.config.ChannelAcquisitionConfig[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ChannelAcquisitionConfigListHelper.type());
		ChannelAcquisitionConfigListHelper.write(out, that);
		a.read_value(out.create_input_stream(), ChannelAcquisitionConfigListHelper.type());
	}

	public static com.linkare.rec.data.config.ChannelAcquisitionConfig[] extract(final org.omg.CORBA.Any a) {
		return ChannelAcquisitionConfigListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ChannelAcquisitionConfigListHelper.__typeCode == null) {
			ChannelAcquisitionConfigListHelper.__typeCode = com.linkare.rec.data.config.ChannelAcquisitionConfigHelper
					.type();
			ChannelAcquisitionConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					ChannelAcquisitionConfigListHelper.__typeCode);
			ChannelAcquisitionConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.config.ChannelAcquisitionConfigListHelper.id(),
					"ChannelAcquisitionConfigList", ChannelAcquisitionConfigListHelper.__typeCode);
		}
		return ChannelAcquisitionConfigListHelper.__typeCode;
	}

	public static String id() {
		return ChannelAcquisitionConfigListHelper._id;
	}

	public static com.linkare.rec.data.config.ChannelAcquisitionConfig[] read(
			final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.config.ChannelAcquisitionConfig value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.config.ChannelAcquisitionConfig[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.config.ChannelAcquisitionConfig[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.write(ostream, value[_i0]);
		}
	}

}
