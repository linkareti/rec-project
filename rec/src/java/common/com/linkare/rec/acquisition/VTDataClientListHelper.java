package com.linkare.rec.acquisition;

public final class VTDataClientListHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/VTDataClientList:1.0";

	private static VTDataClientListHelper _instance = new VTDataClientListHelper();

	public VTDataClientListHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.DataClient[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTDataClientListHelper.type());
		VTDataClientListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTDataClientListHelper.type());
	}

	public static com.linkare.rec.acquisition.DataClient[] extract(final org.omg.CORBA.Any a) {
		return VTDataClientListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTDataClientListHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTDataClientListHelper.__typeCode == null) {
					if (VTDataClientListHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTDataClientListHelper._id);
					}
					VTDataClientListHelper.__active = true;
					VTDataClientListHelper.__typeCode = com.linkare.rec.acquisition.DataClientHelper.type();
					VTDataClientListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTDataClientListHelper.__typeCode);
					VTDataClientListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.acquisition.DataClientListHelper.id(), "DataClientList",
							VTDataClientListHelper.__typeCode);
					VTDataClientListHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTDataClientListHelper._id, "VTDataClientList", VTDataClientListHelper.__typeCode);
					VTDataClientListHelper.__active = false;
				}
			}
		}
		return VTDataClientListHelper.__typeCode;
	}

	public static String id() {
		return VTDataClientListHelper._id;
	}

	public static com.linkare.rec.acquisition.DataClient[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.acquisition.DataClient[]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTDataClientListHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.acquisition.DataClient[] tmp;
		tmp = com.linkare.rec.acquisition.DataClientListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.DataClient[] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTDataClientListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.acquisition.DataClient[])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.acquisition.DataClient[] valueType = (com.linkare.rec.acquisition.DataClient[]) value;
		com.linkare.rec.acquisition.DataClientListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTDataClientListHelper._id;
	}

}
