package com.linkare.rec.data.metadata;

public final class VTFrequencyScaleListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTFrequencyScaleList:1.0";

	private static VTFrequencyScaleListHelper _instance = new VTFrequencyScaleListHelper();

	public VTFrequencyScaleListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.FrequencyScale[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTFrequencyScaleListHelper.type());
		VTFrequencyScaleListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTFrequencyScaleListHelper.type());
	}

	public static com.linkare.rec.data.metadata.FrequencyScale[] extract(final org.omg.CORBA.Any a) {
		return VTFrequencyScaleListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTFrequencyScaleListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTFrequencyScaleListHelper.__typeCode == null) {
					if (VTFrequencyScaleListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTFrequencyScaleListHelper._id);
					}
					VTFrequencyScaleListHelper.__active = true;
					VTFrequencyScaleListHelper.__typeCode = com.linkare.rec.data.metadata.FrequencyScaleHelper.type();
					VTFrequencyScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTFrequencyScaleListHelper.__typeCode);
					VTFrequencyScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.FrequencyScaleListHelper.id(), "FrequencyScaleList",
							VTFrequencyScaleListHelper.__typeCode);
					VTFrequencyScaleListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTFrequencyScaleListHelper._id, "VTFrequencyScaleList",
							VTFrequencyScaleListHelper.__typeCode);
					VTFrequencyScaleListHelper.__active = false;
				}
			}
		}
		return VTFrequencyScaleListHelper.__typeCode;
	}

	public static String id() {
		return VTFrequencyScaleListHelper._id;
	}

	public static com.linkare.rec.data.metadata.FrequencyScale[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.metadata.FrequencyScale[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTFrequencyScaleListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.metadata.FrequencyScale[] tmp;
		tmp = com.linkare.rec.data.metadata.FrequencyScaleListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.FrequencyScale[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTFrequencyScaleListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.metadata.FrequencyScale[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.metadata.FrequencyScale[] valueType = (com.linkare.rec.data.metadata.FrequencyScale[]) value;
		com.linkare.rec.data.metadata.FrequencyScaleListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTFrequencyScaleListHelper._id;
	}

}
