package com.linkare.rec.data.metadata;

abstract public class HardwareInfoListHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/HardwareInfoList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.HardwareInfo[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareInfoListHelper.type());
		HardwareInfoListHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareInfoListHelper.type());
	}

	public static com.linkare.rec.data.metadata.HardwareInfo[] extract(final org.omg.CORBA.Any a) {
		return HardwareInfoListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareInfoListHelper.__typeCode == null) {
			HardwareInfoListHelper.__typeCode = com.linkare.rec.data.metadata.HardwareInfoHelper.type();
			HardwareInfoListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					HardwareInfoListHelper.__typeCode);
			HardwareInfoListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.HardwareInfoListHelper.id(), "HardwareInfoList",
					HardwareInfoListHelper.__typeCode);
		}
		return HardwareInfoListHelper.__typeCode;
	}

	public static String id() {
		return HardwareInfoListHelper._id;
	}

	public static com.linkare.rec.data.metadata.HardwareInfo[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.HardwareInfo value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.metadata.HardwareInfo[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.metadata.HardwareInfoHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.HardwareInfo[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.metadata.HardwareInfoHelper.write(ostream, value[_i0]);
		}
	}

}
