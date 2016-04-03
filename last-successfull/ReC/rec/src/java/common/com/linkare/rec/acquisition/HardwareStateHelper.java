package com.linkare.rec.acquisition;

abstract public class HardwareStateHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/HardwareState:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.HardwareState that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareStateHelper.type());
		HardwareStateHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareStateHelper.type());
	}

	public static com.linkare.rec.acquisition.HardwareState extract(final org.omg.CORBA.Any a) {
		return HardwareStateHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareStateHelper.__typeCode == null) {
			HardwareStateHelper.__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.acquisition.HardwareStateHelper.id(),
					"HardwareState",
					new String[] { "UNKNOWN", "CONFIGURING", "CONFIGURED", "STARTING", "STARTED", "STOPING", "STOPED",
							"RESETING", "RESETED" });
		}
		return HardwareStateHelper.__typeCode;
	}

	public static String id() {
		return HardwareStateHelper._id;
	}

	public static com.linkare.rec.acquisition.HardwareState read(final org.omg.CORBA.portable.InputStream istream) {
		return new com.linkare.rec.acquisition.HardwareState(istream.read_octet());
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.HardwareState value) {
		ostream.write_octet(value.getValue());
	}

}
