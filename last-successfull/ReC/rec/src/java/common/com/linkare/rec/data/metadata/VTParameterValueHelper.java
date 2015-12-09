package com.linkare.rec.data.metadata;

public final class VTParameterValueHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTParameterValue:1.0";

	private static VTParameterValueHelper _instance = new VTParameterValueHelper();

	public VTParameterValueHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final String that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTParameterValueHelper.type());
		VTParameterValueHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTParameterValueHelper.type());
	}

	public static String extract(final org.omg.CORBA.Any a) {
		return VTParameterValueHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTParameterValueHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTParameterValueHelper.__typeCode == null) {
					if (VTParameterValueHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTParameterValueHelper._id);
					}
					VTParameterValueHelper.__active = true;
					VTParameterValueHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					VTParameterValueHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTParameterValueHelper._id, "VTParameterValue", VTParameterValueHelper.__typeCode);
					VTParameterValueHelper.__active = false;
				}
			}
		}
		return VTParameterValueHelper.__typeCode;
	}

	public static String id() {
		return VTParameterValueHelper._id;
	}

	public static String read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (String) ((org.omg.CORBA_2_3.portable.InputStream) istream).read_value(VTParameterValueHelper._instance);
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
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTParameterValueHelper._instance);
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
		return VTParameterValueHelper._id;
	}

}
