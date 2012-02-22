package com.linkare.rec.data.synch;

public final class VTFrequencyHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/VTFrequency:1.0";

	private static VTFrequencyHelper _instance = new VTFrequencyHelper();

	public VTFrequencyHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.synch.Frequency that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTFrequencyHelper.type());
		VTFrequencyHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTFrequencyHelper.type());
	}

	public static com.linkare.rec.data.synch.Frequency extract(final org.omg.CORBA.Any a) {
		return VTFrequencyHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTFrequencyHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTFrequencyHelper.__typeCode == null) {
					if (VTFrequencyHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTFrequencyHelper._id);
					}
					VTFrequencyHelper.__active = true;
					VTFrequencyHelper.__typeCode = com.linkare.rec.data.synch.FrequencyHelper.type();
					VTFrequencyHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(VTFrequencyHelper._id,
							"VTFrequency", VTFrequencyHelper.__typeCode);
					VTFrequencyHelper.__active = false;
				}
			}
		}
		return VTFrequencyHelper.__typeCode;
	}

	public static String id() {
		return VTFrequencyHelper._id;
	}

	public static com.linkare.rec.data.synch.Frequency read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.synch.Frequency) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTFrequencyHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.synch.Frequency tmp = com.linkare.rec.data.synch.FrequencyHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.synch.Frequency value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTFrequencyHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.synch.Frequency)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.synch.Frequency valueType = (com.linkare.rec.data.synch.Frequency) value;
		com.linkare.rec.data.synch.FrequencyHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTFrequencyHelper._id;
	}

}
