package com.linkare.rec.data.metadata;

abstract public class ChannelDirectionHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ChannelDirection:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelDirection that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ChannelDirectionHelper.type());
		ChannelDirectionHelper.write(out, that);
		a.read_value(out.create_input_stream(), ChannelDirectionHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelDirection extract(final org.omg.CORBA.Any a) {
		return ChannelDirectionHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ChannelDirectionHelper.__typeCode == null) {
			ChannelDirectionHelper.__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.data.metadata.ChannelDirectionHelper.id(), "ChannelDirection",
					new String[] { "CHANNEL_INPUT", "CHANNEL_OUTPUT" });
		}
		return ChannelDirectionHelper.__typeCode;
	}

	public static String id() {
		return ChannelDirectionHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelDirection read(final org.omg.CORBA.portable.InputStream istream) {
		return new ChannelDirection(istream.read_ulong());
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelDirection value) {
		ostream.write_ulong(value.getValue());
	}

}
