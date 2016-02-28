package com.linkare.rec.data.metadata;

abstract public class SamplesNumScaleHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/SamplesNumScale:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.SamplesNumScale that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(SamplesNumScaleHelper.type());
		SamplesNumScaleHelper.write(out, that);
		a.read_value(out.create_input_stream(), SamplesNumScaleHelper.type());
	}

	public static com.linkare.rec.data.metadata.SamplesNumScale extract(final org.omg.CORBA.Any a) {
		return SamplesNumScaleHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (SamplesNumScaleHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (SamplesNumScaleHelper.__typeCode == null) {
					if (SamplesNumScaleHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(SamplesNumScaleHelper._id);
					}
					SamplesNumScaleHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[3];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[0] = new org.omg.CORBA.StructMember("min_samples", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[1] = new org.omg.CORBA.StructMember("max_samples", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[2] = new org.omg.CORBA.StructMember("step", _tcOf_members0, null);
					SamplesNumScaleHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.metadata.SamplesNumScaleHelper.id(), "SamplesNumScale", _members0);
					SamplesNumScaleHelper.__active = false;
				}
			}
		}
		return SamplesNumScaleHelper.__typeCode;
	}

	public static String id() {
		return SamplesNumScaleHelper._id;
	}

	public static com.linkare.rec.data.metadata.SamplesNumScale read(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.metadata.SamplesNumScale new_one = new com.linkare.rec.data.metadata.SamplesNumScale();
		new_one.setMinSamples(istream.read_long());
		new_one.setMaxSamples(istream.read_long());
		new_one.setStep(istream.read_long());
		return new_one;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.SamplesNumScale value) {
		ostream.write_long(value.getMinSamples());
		ostream.write_long(value.getMaxSamples());
		ostream.write_long(value.getStep());
	}

}
