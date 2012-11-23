package com.linkare.rec.data.metadata;

public final class VTScaleHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/VTScale:1.0";

	private static VTScaleHelper _instance = new VTScaleHelper();

	public VTScaleHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.Scale that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTScaleHelper.type());
		VTScaleHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTScaleHelper.type());
	}

	public static com.linkare.rec.data.metadata.Scale extract(final org.omg.CORBA.Any a) {
		return VTScaleHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTScaleHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTScaleHelper.__typeCode == null) {
					if (VTScaleHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTScaleHelper._id);
					}
					VTScaleHelper.__active = true;
					VTScaleHelper.__typeCode = com.linkare.rec.data.metadata.ScaleHelper.type();
					VTScaleHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(VTScaleHelper._id,
							"VTScale", VTScaleHelper.__typeCode);
					VTScaleHelper.__active = false;
				}
			}
		}
		return VTScaleHelper.__typeCode;
	}

	public static String id() {
		return VTScaleHelper._id;
	}

	public static com.linkare.rec.data.metadata.Scale read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.metadata.Scale) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTScaleHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.metadata.Scale tmp = com.linkare.rec.data.metadata.ScaleHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.Scale value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTScaleHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.metadata.Scale)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.metadata.Scale valueType = (com.linkare.rec.data.metadata.Scale) value;
		com.linkare.rec.data.metadata.ScaleHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTScaleHelper._id;
	}

}
