package com.linkare.rec.data.metadata;

abstract public class ChannelInfoListHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ChannelInfoList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelInfo[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ChannelInfoListHelper.type());
		ChannelInfoListHelper.write(out, that);
		a.read_value(out.create_input_stream(), ChannelInfoListHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelInfo[] extract(final org.omg.CORBA.Any a) {
		return ChannelInfoListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ChannelInfoListHelper.__typeCode == null) {
			ChannelInfoListHelper.__typeCode = com.linkare.rec.data.metadata.ChannelInfoHelper.type();
			ChannelInfoListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					ChannelInfoListHelper.__typeCode);
			ChannelInfoListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ChannelInfoListHelper.id(), "ChannelInfoList",
					ChannelInfoListHelper.__typeCode);
		}
		return ChannelInfoListHelper.__typeCode;
	}

	public static String id() {
		return ChannelInfoListHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelInfo[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.ChannelInfo value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.metadata.ChannelInfo[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.metadata.ChannelInfoHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelInfo[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.metadata.ChannelInfoHelper.write(ostream, value[_i0]);
		}
	}

}
