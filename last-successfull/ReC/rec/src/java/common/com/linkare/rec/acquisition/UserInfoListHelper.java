package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/UserInfoListHelper.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

// Version 7.0 Addition
abstract public class UserInfoListHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/UserInfoList:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.acquisition.UserInfo[] that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.acquisition.UserInfo[] extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			__typeCode = com.linkare.rec.acquisition.UserInfoHelper.type();
			__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0, __typeCode);
			__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(com.linkare.rec.acquisition.UserInfoListHelper.id(),
					"UserInfoList", __typeCode);
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.acquisition.UserInfo[] read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.acquisition.UserInfo value[] = null;
		int _len0 = istream.read_long();
		value = new com.linkare.rec.acquisition.UserInfo[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1)
			value[_o1] = com.linkare.rec.acquisition.UserInfoHelper.read(istream);
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.acquisition.UserInfo[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0)
			com.linkare.rec.acquisition.UserInfoHelper.write(ostream, value[_i0]);
	}

}
