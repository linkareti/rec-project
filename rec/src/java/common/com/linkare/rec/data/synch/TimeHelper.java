package com.linkare.rec.data.synch;

abstract public class TimeHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/Time:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.synch.Time that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.synch.Time extract(org.omg.CORBA.Any a) {
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[7];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
					_members0[0] = new org.omg.CORBA.StructMember("picos", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
					_members0[1] = new org.omg.CORBA.StructMember("nanos", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
					_members0[2] = new org.omg.CORBA.StructMember("micros", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
					_members0[3] = new org.omg.CORBA.StructMember("milis", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
					_members0[4] = new org.omg.CORBA.StructMember("seconds", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
					_members0[5] = new org.omg.CORBA.StructMember("minutes", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
					_members0[6] = new org.omg.CORBA.StructMember("hours", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(com.linkare.rec.data.synch.TimeHelper.id(),
							"Time", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.synch.Time read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.synch.Time new_one = new com.linkare.rec.data.synch.Time();

		new_one.setPicos(istream.read_short());
		new_one.setNanos(istream.read_short());
		new_one.setMicros(istream.read_short());
		new_one.setMilis(istream.read_short());
		new_one.setSeconds(istream.read_octet());
		new_one.setMinutes(istream.read_octet());
		new_one.setHours(istream.read_octet());

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.data.synch.Time value) {
		ostream.write_short(value.getPicos());
		ostream.write_short(value.getNanos());
		ostream.write_short(value.getMicros());
		ostream.write_short(value.getMilis());
		ostream.write_octet(value.getSeconds());
		ostream.write_octet(value.getMinutes());
		ostream.write_octet(value.getHours());
	}

}
