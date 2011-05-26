package com.linkare.rec.data.metadata;

public final class VTChannelParameterListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTChannelParameterList:1.0";

	private static VTChannelParameterListHelper _instance = new VTChannelParameterListHelper();

	public VTChannelParameterListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelParameter[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTChannelParameterListHelper.type());
		VTChannelParameterListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTChannelParameterListHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelParameter[] extract(final org.omg.CORBA.Any a) {
		return VTChannelParameterListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTChannelParameterListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTChannelParameterListHelper.__typeCode == null) {
					if (VTChannelParameterListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTChannelParameterListHelper._id);
					}
					VTChannelParameterListHelper.__active = true;
					VTChannelParameterListHelper.__typeCode = com.linkare.rec.data.metadata.ChannelParameterHelper
							.type();
					VTChannelParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTChannelParameterListHelper.__typeCode);
					VTChannelParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ChannelParameterListHelper.id(), "ChannelParameterList",
							VTChannelParameterListHelper.__typeCode);
					VTChannelParameterListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTChannelParameterListHelper._id, "VTChannelParameterList",
							VTChannelParameterListHelper.__typeCode);
					VTChannelParameterListHelper.__active = false;
				}
			}
		}
		return VTChannelParameterListHelper.__typeCode;
	}

	public static String id() {
		return VTChannelParameterListHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelParameter[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.metadata.ChannelParameter[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTChannelParameterListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.ChannelParameter[] tmp;
		tmp = com.linkare.rec.data.metadata.ChannelParameterListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelParameter[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTChannelParameterListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.metadata.ChannelParameter[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.metadata.ChannelParameter[] valueType = (com.linkare.rec.data.metadata.ChannelParameter[]) value;
		com.linkare.rec.data.metadata.ChannelParameterListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTChannelParameterListHelper._id;
	}

}
