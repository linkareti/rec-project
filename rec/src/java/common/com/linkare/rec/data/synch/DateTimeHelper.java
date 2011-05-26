package com.linkare.rec.data.synch;

abstract public class DateTimeHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/DateTime:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.synch.DateTime that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(DateTimeHelper.type());
		DateTimeHelper.write(out, that);
		a.read_value(out.create_input_stream(), DateTimeHelper.type());
	}

	public static com.linkare.rec.data.synch.DateTime extract(final org.omg.CORBA.Any a) {
		return DateTimeHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (DateTimeHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (DateTimeHelper.__typeCode == null) {
					if (DateTimeHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(DateTimeHelper._id);
					}
					DateTimeHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[2];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.synch.DateHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember("date", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.synch.TimeHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember("time", _tcOf_members0, null);
					DateTimeHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.synch.DateTimeHelper.id(), "DateTime", _members0);
					DateTimeHelper.__active = false;
				}
			}
		}
		return DateTimeHelper.__typeCode;
	}

	public static String id() {
		return DateTimeHelper._id;
	}

	public static com.linkare.rec.data.synch.DateTime read(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.synch.DateTime new_one = new com.linkare.rec.data.synch.DateTime();

		new_one.setDate(com.linkare.rec.data.synch.DateHelper.read(istream));
		new_one.setTime(com.linkare.rec.data.synch.TimeHelper.read(istream));

		return new_one;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.synch.DateTime value) {

		com.linkare.rec.data.synch.DateHelper.write(ostream, value.getDate());
		com.linkare.rec.data.synch.TimeHelper.write(ostream, value.getTime());

	}

}
