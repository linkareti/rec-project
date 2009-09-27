package com.linkare.rec.data.synch;

abstract public class DateTimeHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/DateTime:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.synch.DateTime that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.synch.DateTime extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (__typeCode == null) {
					if (__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(_id);
					}
					__active = true;
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[2];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.synch.DateHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember("date", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.synch.TimeHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember("time", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.synch.DateTimeHelper.id(), "DateTime", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.synch.DateTime read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.synch.DateTime new_one = new com.linkare.rec.data.synch.DateTime();

		new_one.setDate(com.linkare.rec.data.synch.DateHelper.read(istream));
		new_one.setTime(com.linkare.rec.data.synch.TimeHelper.read(istream));

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.synch.DateTime value) {

		com.linkare.rec.data.synch.DateHelper.write(ostream, value.getDate());
		com.linkare.rec.data.synch.TimeHelper.write(ostream, value.getTime());

	}

}
