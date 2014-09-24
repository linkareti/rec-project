package com.linkare.rec.data.metadata;

public final class VTHardwareParameterListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTHardwareParameterList:1.0";

	private static VTHardwareParameterListHelper _instance = new VTHardwareParameterListHelper();

	public VTHardwareParameterListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelParameter[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTHardwareParameterListHelper.type());
		VTHardwareParameterListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTHardwareParameterListHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelParameter[] extract(final org.omg.CORBA.Any a) {
		return VTHardwareParameterListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTHardwareParameterListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTHardwareParameterListHelper.__typeCode == null) {
					if (VTHardwareParameterListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTHardwareParameterListHelper._id);
					}
					VTHardwareParameterListHelper.__active = true;
					VTHardwareParameterListHelper.__typeCode = com.linkare.rec.data.metadata.ChannelParameterHelper
							.type();
					VTHardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.HardwareParameterHelper.id(), "HardwareParameter",
							VTHardwareParameterListHelper.__typeCode);
					VTHardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTHardwareParameterListHelper.__typeCode);
					VTHardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.HardwareParameterListHelper.id(), "HardwareParameterList",
							VTHardwareParameterListHelper.__typeCode);
					VTHardwareParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTHardwareParameterListHelper._id, "VTHardwareParameterList",
							VTHardwareParameterListHelper.__typeCode);
					VTHardwareParameterListHelper.__active = false;
				}
			}
		}
		return VTHardwareParameterListHelper.__typeCode;
	}

	public static String id() {
		return VTHardwareParameterListHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelParameter[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.metadata.ChannelParameter[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTHardwareParameterListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.ChannelParameter[] tmp;
		tmp = com.linkare.rec.data.metadata.HardwareParameterListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelParameter[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTHardwareParameterListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.metadata.ChannelParameter[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.metadata.ChannelParameter[] valueType = (com.linkare.rec.data.metadata.ChannelParameter[]) value;
		com.linkare.rec.data.metadata.HardwareParameterListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTHardwareParameterListHelper._id;
	}

}
