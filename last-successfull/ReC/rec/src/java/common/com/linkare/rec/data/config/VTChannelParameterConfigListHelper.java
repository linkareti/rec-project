package com.linkare.rec.data.config;

public final class VTChannelParameterConfigListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/config/VTChannelParameterConfigList:1.0";

	private static VTChannelParameterConfigListHelper _instance = new VTChannelParameterConfigListHelper();

	public VTChannelParameterConfigListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.config.ParameterConfig[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTChannelParameterConfigListHelper.type());
		VTChannelParameterConfigListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTChannelParameterConfigListHelper.type());
	}

	public static com.linkare.rec.data.config.ParameterConfig[] extract(final org.omg.CORBA.Any a) {
		return VTChannelParameterConfigListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTChannelParameterConfigListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTChannelParameterConfigListHelper.__typeCode == null) {
					if (VTChannelParameterConfigListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTChannelParameterConfigListHelper._id);
					}
					VTChannelParameterConfigListHelper.__active = true;
					VTChannelParameterConfigListHelper.__typeCode = com.linkare.rec.data.config.ParameterConfigHelper
							.type();
					VTChannelParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTChannelParameterConfigListHelper.__typeCode);
					VTChannelParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.config.ParameterConfigListHelper.id(), "ParameterConfigList",
							VTChannelParameterConfigListHelper.__typeCode);
					VTChannelParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTChannelParameterConfigListHelper._id, "VTChannelParameterConfigList",
							VTChannelParameterConfigListHelper.__typeCode);
					VTChannelParameterConfigListHelper.__active = false;
				}
			}
		}
		return VTChannelParameterConfigListHelper.__typeCode;
	}

	public static String id() {
		return VTChannelParameterConfigListHelper._id;
	}

	public static com.linkare.rec.data.config.ParameterConfig[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.config.ParameterConfig[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTChannelParameterConfigListHelper._instance);
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
				VTChannelParameterConfigListHelper._instance);
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
		return VTChannelParameterConfigListHelper._id;
	}

}
