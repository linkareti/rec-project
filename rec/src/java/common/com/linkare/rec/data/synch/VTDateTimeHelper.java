package com.linkare.rec.data.synch;

public final class VTDateTimeHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/VTDateTime:1.0";
	private static VTDateTimeHelper _instance = new VTDateTimeHelper();

	public VTDateTimeHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.synch.DateTime that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTDateTimeHelper.type());
		VTDateTimeHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTDateTimeHelper.type());
	}

	public static com.linkare.rec.data.synch.DateTime extract(final org.omg.CORBA.Any a) {
		return VTDateTimeHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTDateTimeHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTDateTimeHelper.__typeCode == null) {
					if (VTDateTimeHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTDateTimeHelper._id);
					}
					VTDateTimeHelper.__active = true;
					VTDateTimeHelper.__typeCode = com.linkare.rec.data.synch.DateTimeHelper.type();
					VTDateTimeHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(VTDateTimeHelper._id,
							"VTDateTime", VTDateTimeHelper.__typeCode);
					VTDateTimeHelper.__active = false;
				}

			}

		}

		return VTDateTimeHelper.__typeCode;
	}

	public static String id() {
		return VTDateTimeHelper._id;
	}

	public static com.linkare.rec.data.synch.DateTime read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.synch.DateTime) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTDateTimeHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.synch.DateTime tmp = com.linkare.rec.data.synch.DateTimeHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.synch.DateTime value) {

		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {

			throw new org.omg.CORBA.BAD_PARAM();
		}

		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTDateTimeHelper._instance);

	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {

		if (value != null && !(value instanceof com.linkare.rec.data.synch.DateTime)) {
			throw new org.omg.CORBA.MARSHAL();
		}

		final com.linkare.rec.data.synch.DateTime valueType = (com.linkare.rec.data.synch.DateTime) value;
		com.linkare.rec.data.synch.DateTimeHelper.write(ostream, valueType);

	}

	@Override
	public String get_id() {
		return VTDateTimeHelper._id;
	}

}
