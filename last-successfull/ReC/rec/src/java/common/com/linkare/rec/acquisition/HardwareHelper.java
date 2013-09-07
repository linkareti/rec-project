package com.linkare.rec.acquisition;

abstract public class HardwareHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/Hardware:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.Hardware that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareHelper.type());
		HardwareHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareHelper.type());
	}

	public static com.linkare.rec.acquisition.Hardware extract(final org.omg.CORBA.Any a) {
		return HardwareHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareHelper.__typeCode == null) {
			HardwareHelper.__typeCode = org.omg.CORBA.ORB.init().create_interface_tc(
					com.linkare.rec.acquisition.HardwareHelper.id(), "Hardware");
		}
		return HardwareHelper.__typeCode;
	}

	public static String id() {
		return HardwareHelper._id;
	}

	public static com.linkare.rec.acquisition.Hardware read(final org.omg.CORBA.portable.InputStream istream) {
		return HardwareHelper.narrow(istream.read_Object(_HardwareStub.class));
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.Hardware value) {
		ostream.write_Object(value);
	}

	public static com.linkare.rec.acquisition.Hardware narrow(final org.omg.CORBA.Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof com.linkare.rec.acquisition.Hardware) {
			return (com.linkare.rec.acquisition.Hardware) obj;
		} else if (!obj._is_a(HardwareHelper.id())) {
			throw new org.omg.CORBA.BAD_PARAM();
		} else {
			final org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
			final com.linkare.rec.acquisition._HardwareStub stub = new com.linkare.rec.acquisition._HardwareStub();
			stub._set_delegate(delegate);
			return stub;
		}
	}

}
