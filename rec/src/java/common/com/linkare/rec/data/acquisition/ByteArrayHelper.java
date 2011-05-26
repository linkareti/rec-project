package com.linkare.rec.data.acquisition;

abstract public class ByteArrayHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/ByteArray:1.0";

	public static void insert(final org.omg.CORBA.Any a, final byte[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ByteArrayHelper.type());
		ByteArrayHelper.write(out, that);
		a.read_value(out.create_input_stream(), ByteArrayHelper.type());
	}

	public static byte[] extract(final org.omg.CORBA.Any a) {
		return ByteArrayHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ByteArrayHelper.__typeCode == null) {
			ByteArrayHelper.__typeCode = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
			ByteArrayHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0, ByteArrayHelper.__typeCode);
			ByteArrayHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.acquisition.ByteArrayHelper.id(), "ByteArray", ByteArrayHelper.__typeCode);
		}
		return ByteArrayHelper.__typeCode;
	}

	public static String id() {
		return ByteArrayHelper._id;
	}

	public static byte[] read(final org.omg.CORBA.portable.InputStream istream) {
		byte value[] = null;
		final int _len0 = istream.read_long();
		value = new byte[_len0];
		istream.read_octet_array(value, 0, _len0);
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final byte[] value) {
		ostream.write_long(value.length);
		ostream.write_octet_array(value, 0, value.length);
	}

}
