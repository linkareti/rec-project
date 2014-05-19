package com.linkare.rec.acquisition;

public final class VTMultiCastHardwareListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/VTMultiCastHardwareList:1.0";

	private static VTMultiCastHardwareListHelper _instance = new VTMultiCastHardwareListHelper();

	public VTMultiCastHardwareListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.MultiCastHardware[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTMultiCastHardwareListHelper.type());
		VTMultiCastHardwareListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTMultiCastHardwareListHelper.type());
	}

	public static com.linkare.rec.acquisition.MultiCastHardware[] extract(final org.omg.CORBA.Any a) {
		return VTMultiCastHardwareListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTMultiCastHardwareListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTMultiCastHardwareListHelper.__typeCode == null) {
					if (VTMultiCastHardwareListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTMultiCastHardwareListHelper._id);
					}
					VTMultiCastHardwareListHelper.__active = true;
					VTMultiCastHardwareListHelper.__typeCode = com.linkare.rec.acquisition.MultiCastHardwareHelper
							.type();
					VTMultiCastHardwareListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTMultiCastHardwareListHelper.__typeCode);
					VTMultiCastHardwareListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.acquisition.MultiCastHardwareListHelper.id(), "MultiCastHardwareList",
							VTMultiCastHardwareListHelper.__typeCode);
					VTMultiCastHardwareListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTMultiCastHardwareListHelper._id, "VTMultiCastHardwareList",
							VTMultiCastHardwareListHelper.__typeCode);
					VTMultiCastHardwareListHelper.__active = false;
				}
			}
		}
		return VTMultiCastHardwareListHelper.__typeCode;
	}

	public static String id() {
		return VTMultiCastHardwareListHelper._id;
	}

	public static com.linkare.rec.acquisition.MultiCastHardware[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.acquisition.MultiCastHardware[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTMultiCastHardwareListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.acquisition.MultiCastHardware[] tmp;
		tmp = com.linkare.rec.acquisition.MultiCastHardwareListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.MultiCastHardware[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTMultiCastHardwareListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.acquisition.MultiCastHardware[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.acquisition.MultiCastHardware[] valueType = (com.linkare.rec.acquisition.MultiCastHardware[]) value;
		com.linkare.rec.acquisition.MultiCastHardwareListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTMultiCastHardwareListHelper._id;
	}

}
