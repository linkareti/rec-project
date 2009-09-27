package com.linkare.rec.data.metadata;

public final class VTParameterValueHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTParameterValue:1.0";

	private static VTParameterValueHelper _instance = new VTParameterValueHelper();

	public VTParameterValueHelper() {
	}

	public static void insert(org.omg.CORBA.Any a, String that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static String extract(org.omg.CORBA.Any a) {
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
					__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(_id, "VTParameterValue", __typeCode);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static String read(org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (String) ((org.omg.CORBA_2_3.portable.InputStream) istream).read_value(_instance);
	}

	public java.io.Serializable read_value(org.omg.CORBA.portable.InputStream istream) {
		String tmp;
		tmp = istream.read_wstring();
		return (java.io.Serializable) tmp;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, String value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, _instance);
	}

	public void write_value(org.omg.CORBA.portable.OutputStream ostream, java.io.Serializable value) {
		if (!(value instanceof String)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		String valueType = (String) value;
		ostream.write_wstring(valueType);
	}

	public String get_id() {
		return _id;
	}

}
