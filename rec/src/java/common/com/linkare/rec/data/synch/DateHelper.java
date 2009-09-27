package com.linkare.rec.data.synch;

abstract public class DateHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/Date:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.synch.Date that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.synch.Date extract(org.omg.CORBA.Any a) {
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[3];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
					_members0[0] = new org.omg.CORBA.StructMember("day", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
					_members0[1] = new org.omg.CORBA.StructMember("month", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
					_members0[2] = new org.omg.CORBA.StructMember("year", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.synch.DateHelper.id(),
							"Date", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.synch.Date read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.synch.Date new_one = new com.linkare.rec.data.synch.Date();

		new_one.setDay(istream.read_octet());
		new_one.setMonth(istream.read_octet());
		new_one.setYear(istream.read_short());

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.synch.Date value) {
		ostream.write_octet(value.getDay());
		ostream.write_octet(value.getMonth());
		ostream.write_short(value.getYear());
	}

}
