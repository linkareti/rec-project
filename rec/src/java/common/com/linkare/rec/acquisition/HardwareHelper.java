package com.linkare.rec.acquisition;

abstract public class HardwareHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/Hardware:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.acquisition.Hardware that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.acquisition.Hardware extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			__typeCode = org.omg.CORBA.ORB.init().create_interface_tc(com.linkare.rec.acquisition.HardwareHelper.id(),
					"Hardware");
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.acquisition.Hardware read(org.omg.CORBA.portable.InputStream istream) {
		return narrow(istream.read_Object(_HardwareStub.class));
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.acquisition.Hardware value) {
		ostream.write_Object((org.omg.CORBA.Object) value);
	}

	public static com.linkare.rec.acquisition.Hardware narrow(org.omg.CORBA.Object obj) {
		if (obj == null)
			return null;
		else if (obj instanceof com.linkare.rec.acquisition.Hardware)
			return (com.linkare.rec.acquisition.Hardware) obj;
		else if (!obj._is_a(id()))
			throw new org.omg.CORBA.BAD_PARAM();
		else {
			org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
			com.linkare.rec.acquisition._HardwareStub stub = new com.linkare.rec.acquisition._HardwareStub();
			stub._set_delegate(delegate);
			return stub;
		}
	}

}
