package com.linkare.rec.data.metadata;

public final class VTSamplesNumScaleHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTSamplesNumScale:1.0";

	private static VTSamplesNumScaleHelper _instance = new VTSamplesNumScaleHelper();

	public VTSamplesNumScaleHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.SamplesNumScale that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTSamplesNumScaleHelper.type());
		VTSamplesNumScaleHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTSamplesNumScaleHelper.type());
	}

	public static com.linkare.rec.data.metadata.SamplesNumScale extract(final org.omg.CORBA.Any a) {
		return VTSamplesNumScaleHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTSamplesNumScaleHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTSamplesNumScaleHelper.__typeCode == null) {
					if (VTSamplesNumScaleHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTSamplesNumScaleHelper._id);
					}
					VTSamplesNumScaleHelper.__active = true;
					VTSamplesNumScaleHelper.__typeCode = com.linkare.rec.data.metadata.SamplesNumScaleHelper.type();
					VTSamplesNumScaleHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTSamplesNumScaleHelper._id, "VTSamplesNumScale", VTSamplesNumScaleHelper.__typeCode);
					VTSamplesNumScaleHelper.__active = false;
				}
			}
		}
		return VTSamplesNumScaleHelper.__typeCode;
	}

	public static String id() {
		return VTSamplesNumScaleHelper._id;
	}

	public static com.linkare.rec.data.metadata.SamplesNumScale read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.metadata.SamplesNumScale) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTSamplesNumScaleHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.metadata.SamplesNumScale tmp = com.linkare.rec.data.metadata.SamplesNumScaleHelper
				.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.SamplesNumScale value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTSamplesNumScaleHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.metadata.SamplesNumScale)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.metadata.SamplesNumScale valueType = (com.linkare.rec.data.metadata.SamplesNumScale) value;
		com.linkare.rec.data.metadata.SamplesNumScaleHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTSamplesNumScaleHelper._id;
	}

}
