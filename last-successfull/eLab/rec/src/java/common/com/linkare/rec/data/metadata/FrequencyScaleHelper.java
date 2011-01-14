package com.linkare.rec.data.metadata;

abstract public class FrequencyScaleHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/FrequencyScale:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.metadata.FrequencyScale that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.metadata.FrequencyScale extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (__typeCode == null) {
					if (__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(_id);
					}
					__active = true;
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[4];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.synch.FrequencyHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember("f_min", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.synch.FrequencyHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember("f_max", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.synch.FrequencyHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember("step", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember("frequency_scale_label", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.metadata.FrequencyScaleHelper.id(), "FrequencyScale", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.metadata.FrequencyScale read(org.omg.CORBA.portable.InputStream istream) {

		com.linkare.rec.data.metadata.FrequencyScale new_one = new com.linkare.rec.data.metadata.FrequencyScale();

		new_one.setMinimumFrequency(com.linkare.rec.data.synch.FrequencyHelper.read(istream));
		new_one.setMaximumFrequency(com.linkare.rec.data.synch.FrequencyHelper.read(istream));
		new_one.setStepFrequency(com.linkare.rec.data.synch.FrequencyHelper.read(istream));
		new_one.setFrequencyScaleLabel(org.omg.CORBA.WStringValueHelper.read(istream));

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.data.metadata.FrequencyScale value) {
		com.linkare.rec.data.synch.FrequencyHelper.write(ostream, value.getMinimumFrequency());
		com.linkare.rec.data.synch.FrequencyHelper.write(ostream, value.getMaximumFrequency());
		com.linkare.rec.data.synch.FrequencyHelper.write(ostream, value.getStepFrequency());
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getFrequencyScaleLabel());
	}

}
