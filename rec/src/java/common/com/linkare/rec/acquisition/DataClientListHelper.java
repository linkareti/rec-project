package com.linkare.rec.acquisition;

//If you identify successfully I give you access to this wonderfull system...
/** CORBA Helper class */
abstract public class DataClientListHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/dataClientList:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.acquisition.DataClient[] that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.acquisition.DataClient[] extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			__typeCode = com.linkare.rec.acquisition.DataClientHelper.type();
			__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0, __typeCode);
			__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.acquisition.DataClientListHelper.id(), "DataClientList", __typeCode);
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.acquisition.DataClient[] read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.acquisition.DataClient value[] = null;
		int _len0 = istream.read_long();
		value = new com.linkare.rec.acquisition.DataClient[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1)
			value[_o1] = com.linkare.rec.acquisition.DataClientHelper.read(istream);
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.acquisition.DataClient[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0)
			com.linkare.rec.acquisition.DataClientHelper.write(ostream, value[_i0]);
	}

}
