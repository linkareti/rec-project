package com.linkare.rec.acquisition;

public final class VTClientNameHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/VTClientName:1.0";

	private static VTClientNameHelper _instance = new VTClientNameHelper();

	public VTClientNameHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final String that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTClientNameHelper.type());
		VTClientNameHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTClientNameHelper.type());
	}

	public static String extract(final org.omg.CORBA.Any a) {
		return VTClientNameHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTClientNameHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTClientNameHelper.__typeCode == null) {
					if (VTClientNameHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTClientNameHelper._id);
					}
					VTClientNameHelper.__active = true;
					VTClientNameHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					VTClientNameHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTClientNameHelper._id, "VTClientName", VTClientNameHelper.__typeCode);
					VTClientNameHelper.__active = false;
				}
			}
		}
		return VTClientNameHelper.__typeCode;
	}

	public static String id() {
		return VTClientNameHelper._id;
	}

	public static String read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (String) ((org.omg.CORBA_2_3.portable.InputStream) istream).read_value(VTClientNameHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		String tmp;
		tmp = istream.read_wstring();
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final String value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTClientNameHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof String)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final String valueType = (String) value;
		ostream.write_wstring(valueType);
	}

	@Override
	public String get_id() {
		return VTClientNameHelper._id;
	}

}
