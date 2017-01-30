package com.linkare.rec.acquisition;

public final class VTClientNameListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/VTClientNameList:1.0";

	private static VTClientNameListHelper _instance = new VTClientNameListHelper();

	public VTClientNameListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final String[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTClientNameListHelper.type());
		VTClientNameListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTClientNameListHelper.type());
	}

	public static String[] extract(final org.omg.CORBA.Any a) {
		return VTClientNameListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTClientNameListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTClientNameListHelper.__typeCode == null) {
					if (VTClientNameListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTClientNameListHelper._id);
					}
					VTClientNameListHelper.__active = true;
					VTClientNameListHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					VTClientNameListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.acquisition.ClientNameHelper.id(), "ClientName",
							VTClientNameListHelper.__typeCode);
					VTClientNameListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTClientNameListHelper.__typeCode);
					VTClientNameListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.acquisition.ClientNameListHelper.id(), "ClientNameList",
							VTClientNameListHelper.__typeCode);
					VTClientNameListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTClientNameListHelper._id, "VTClientNameList", VTClientNameListHelper.__typeCode);
					VTClientNameListHelper.__active = false;
				}
			}
		}
		return VTClientNameListHelper.__typeCode;
	}

	public static String id() {
		return VTClientNameListHelper._id;
	}

	public static String[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (String[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTClientNameListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		String[] tmp;
		tmp = com.linkare.rec.acquisition.ClientNameListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final String[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTClientNameListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof String[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final String[] valueType = (String[]) value;
		com.linkare.rec.acquisition.ClientNameListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTClientNameListHelper._id;
	}

}
