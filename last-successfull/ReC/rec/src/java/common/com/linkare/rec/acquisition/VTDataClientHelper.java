package com.linkare.rec.acquisition;

public final class VTDataClientHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/VTDataClient:1.0";

	private static VTDataClientHelper _instance = new VTDataClientHelper();

	public VTDataClientHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.DataClient that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTDataClientHelper.type());
		VTDataClientHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTDataClientHelper.type());
	}

	public static com.linkare.rec.acquisition.DataClient extract(final org.omg.CORBA.Any a) {
		return VTDataClientHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTDataClientHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTDataClientHelper.__typeCode == null) {
					if (VTDataClientHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTDataClientHelper._id);
					}
					VTDataClientHelper.__active = true;
					VTDataClientHelper.__typeCode = com.linkare.rec.acquisition.DataClientHelper.type();
					VTDataClientHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTDataClientHelper._id, "VTDataClient", VTDataClientHelper.__typeCode);
					VTDataClientHelper.__active = false;
				}
			}
		}
		return VTDataClientHelper.__typeCode;
	}

	public static String id() {
		return VTDataClientHelper._id;
	}

	public static com.linkare.rec.acquisition.DataClient read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.acquisition.DataClient) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTDataClientHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.acquisition.DataClient tmp = com.linkare.rec.acquisition.DataClientHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.DataClient value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTDataClientHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.acquisition.DataClient)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.acquisition.DataClient valueType = (com.linkare.rec.acquisition.DataClient) value;
		com.linkare.rec.acquisition.DataClientHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTDataClientHelper._id;
	}

}
