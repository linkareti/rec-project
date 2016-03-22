package com.linkare.rec.acquisition;

public final class VTHardwareStateHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/VTHardwareState:1.0";

	private static VTHardwareStateHelper _instance = new VTHardwareStateHelper();

	public VTHardwareStateHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.HardwareState that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTHardwareStateHelper.type());
		VTHardwareStateHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTHardwareStateHelper.type());
	}

	public static com.linkare.rec.acquisition.HardwareState extract(final org.omg.CORBA.Any a) {
		return VTHardwareStateHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTHardwareStateHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTHardwareStateHelper.__typeCode == null) {
					if (VTHardwareStateHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTHardwareStateHelper._id);
					}
					VTHardwareStateHelper.__active = true;
					VTHardwareStateHelper.__typeCode = com.linkare.rec.acquisition.HardwareStateHelper.type();
					VTHardwareStateHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTHardwareStateHelper._id, "VTHardwareState", VTHardwareStateHelper.__typeCode);
					VTHardwareStateHelper.__active = false;
				}
			}
		}
		return VTHardwareStateHelper.__typeCode;
	}

	public static String id() {
		return VTHardwareStateHelper._id;
	}

	public static com.linkare.rec.acquisition.HardwareState read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.acquisition.HardwareState) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTHardwareStateHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.acquisition.HardwareState tmp = com.linkare.rec.acquisition.HardwareStateHelper
				.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.HardwareState value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTHardwareStateHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.acquisition.HardwareState)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.acquisition.HardwareState valueType = (com.linkare.rec.acquisition.HardwareState) value;
		com.linkare.rec.acquisition.HardwareStateHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTHardwareStateHelper._id;
	}

}
