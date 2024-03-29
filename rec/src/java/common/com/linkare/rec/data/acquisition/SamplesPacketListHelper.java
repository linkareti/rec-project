package com.linkare.rec.data.acquisition;

abstract public class SamplesPacketListHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/SamplesPacketList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.acquisition.SamplesPacket[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(SamplesPacketListHelper.type());
		SamplesPacketListHelper.write(out, that);
		a.read_value(out.create_input_stream(), SamplesPacketListHelper.type());
	}

	public static com.linkare.rec.data.acquisition.SamplesPacket[] extract(final org.omg.CORBA.Any a) {
		return SamplesPacketListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (SamplesPacketListHelper.__typeCode == null) {
			SamplesPacketListHelper.__typeCode = com.linkare.rec.data.acquisition.SamplesPacketHelper.type();
			SamplesPacketListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					SamplesPacketListHelper.__typeCode);
			SamplesPacketListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.acquisition.SamplesPacketListHelper.id(), "SamplesPacketList",
					SamplesPacketListHelper.__typeCode);
		}
		return SamplesPacketListHelper.__typeCode;
	}

	public static String id() {
		return SamplesPacketListHelper._id;
	}

	public static com.linkare.rec.data.acquisition.SamplesPacket[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.acquisition.SamplesPacket value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.acquisition.SamplesPacket[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.acquisition.SamplesPacketHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.acquisition.SamplesPacket[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.acquisition.SamplesPacketHelper.write(ostream, value[_i0]);
		}
	}

}
