package com.linkare.rec.data.config;

public final class VTHardwareParameterConfigListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/config/VTHardwareParameterConfigList:1.0";

	private static VTHardwareParameterConfigListHelper _instance = new VTHardwareParameterConfigListHelper();

	public VTHardwareParameterConfigListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.config.ParameterConfig[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTHardwareParameterConfigListHelper.type());
		VTHardwareParameterConfigListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTHardwareParameterConfigListHelper.type());
	}

	public static com.linkare.rec.data.config.ParameterConfig[] extract(final org.omg.CORBA.Any a) {
		return VTHardwareParameterConfigListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTHardwareParameterConfigListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTHardwareParameterConfigListHelper.__typeCode == null) {
					if (VTHardwareParameterConfigListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTHardwareParameterConfigListHelper._id);
					}
					VTHardwareParameterConfigListHelper.__active = true;
					VTHardwareParameterConfigListHelper.__typeCode = com.linkare.rec.data.config.ParameterConfigHelper
							.type();
					VTHardwareParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTHardwareParameterConfigListHelper.__typeCode);
					VTHardwareParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.config.ParameterConfigListHelper.id(), "ParameterConfigList",
							VTHardwareParameterConfigListHelper.__typeCode);
					VTHardwareParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTHardwareParameterConfigListHelper._id, "VTHardwareParameterConfigList",
							VTHardwareParameterConfigListHelper.__typeCode);
					VTHardwareParameterConfigListHelper.__active = false;
				}
			}
		}
		return VTHardwareParameterConfigListHelper.__typeCode;
	}

	public static String id() {
		return VTHardwareParameterConfigListHelper._id;
	}

	public static com.linkare.rec.data.config.ParameterConfig[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.config.ParameterConfig[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTHardwareParameterConfigListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.config.ParameterConfig[] tmp;
		tmp = com.linkare.rec.data.config.ParameterConfigListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.config.ParameterConfig[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value,
				VTHardwareParameterConfigListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.config.ParameterConfig[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.config.ParameterConfig[] valueType = (com.linkare.rec.data.config.ParameterConfig[]) value;
		com.linkare.rec.data.config.ParameterConfigListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTHardwareParameterConfigListHelper._id;
	}

}
