package com.linkare.rec.data.metadata;

abstract public class ChannelNameHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ChannelName:1.0";

	public static void insert(final org.omg.CORBA.Any a, final String that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ChannelNameHelper.type());
		ChannelNameHelper.write(out, that);
		a.read_value(out.create_input_stream(), ChannelNameHelper.type());
	}

	public static String extract(final org.omg.CORBA.Any a) {
		return ChannelNameHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ChannelNameHelper.__typeCode == null) {
			ChannelNameHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
			ChannelNameHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ChannelNameHelper.id(), "ChannelName", ChannelNameHelper.__typeCode);
		}
		return ChannelNameHelper.__typeCode;
	}

	public static String id() {
		return ChannelNameHelper._id;
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
