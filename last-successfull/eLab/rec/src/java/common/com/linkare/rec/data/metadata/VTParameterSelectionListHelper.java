package com.linkare.rec.data.metadata;

public final class VTParameterSelectionListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTParameterSelectionList:1.0";

	private static VTParameterSelectionListHelper _instance = new VTParameterSelectionListHelper();

	public VTParameterSelectionListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final String[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTParameterSelectionListHelper.type());
		VTParameterSelectionListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTParameterSelectionListHelper.type());
	}

	public static String[] extract(final org.omg.CORBA.Any a) {
		return VTParameterSelectionListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTParameterSelectionListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTParameterSelectionListHelper.__typeCode == null) {
					if (VTParameterSelectionListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTParameterSelectionListHelper._id);
					}
					VTParameterSelectionListHelper.__active = true;
					VTParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					VTParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ParameterValueHelper.id(), "ParameterValue",
							VTParameterSelectionListHelper.__typeCode);
					VTParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTParameterSelectionListHelper.__typeCode);
					VTParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ParameterSelectionListHelper.id(), "ParameterSelectionList",
							VTParameterSelectionListHelper.__typeCode);
					VTParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTParameterSelectionListHelper._id, "VTParameterSelectionList",
							VTParameterSelectionListHelper.__typeCode);
					VTParameterSelectionListHelper.__active = false;
				}
			}
		}
		return VTParameterSelectionListHelper.__typeCode;
	}

	public static String id() {
		return VTParameterSelectionListHelper._id;
	}

	public static String[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (String[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTParameterSelectionListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		String[] tmp;
		tmp = com.linkare.rec.data.metadata.ParameterSelectionListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final String[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream)
				.write_value(value, VTParameterSelectionListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof String[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final String[] valueType = (String[]) value;
		com.linkare.rec.data.metadata.ParameterSelectionListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTParameterSelectionListHelper._id;
	}

}
