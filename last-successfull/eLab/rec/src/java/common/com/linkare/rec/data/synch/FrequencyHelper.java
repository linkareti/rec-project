package com.linkare.rec.data.synch;

abstract public class FrequencyHelper {
	private static String _id = "IDL:com/linkare/rec/data/synch/Frequency:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.synch.Frequency that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(FrequencyHelper.type());
		FrequencyHelper.write(out, that);
		a.read_value(out.create_input_stream(), FrequencyHelper.type());
	}

	public static com.linkare.rec.data.synch.Frequency extract(final org.omg.CORBA.Any a) {
		return FrequencyHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (FrequencyHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (FrequencyHelper.__typeCode == null) {
					if (FrequencyHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(FrequencyHelper._id);
					}
					FrequencyHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[3];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = com.linkare.rec.data.synch.FrequencyDefTypeHelper.type();
					_members0[0] = new org.omg.CORBA.StructMember("frequency_def_type", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_double);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.synch.FrequencyValHelper.id(), "FrequencyVal", _tcOf_members0);
					_members0[1] = new org.omg.CORBA.StructMember("value", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.MultiplierHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember("applied_multiplier", _tcOf_members0, null);
					FrequencyHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.synch.FrequencyHelper.id(), "Frequency", _members0);
					FrequencyHelper.__active = false;
				}
			}
		}
		return FrequencyHelper.__typeCode;
	}

	public static String id() {
		return FrequencyHelper._id;
	}

	public static com.linkare.rec.data.synch.Frequency read(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.synch.Frequency new_one = new com.linkare.rec.data.synch.Frequency();

		new_one.setFrequencyDefType(com.linkare.rec.data.synch.FrequencyDefTypeHelper.read(istream));
		new_one.setFrequency(com.linkare.rec.data.synch.FrequencyValHelper.read(istream));
		new_one.setMultiplier(com.linkare.rec.data.MultiplierHelper.read(istream));

		return new_one;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.synch.Frequency value) {
		com.linkare.rec.data.synch.FrequencyDefTypeHelper.write(ostream, value.getFrequencyDefType());
		com.linkare.rec.data.synch.FrequencyValHelper.write(ostream, value.getFrequency());
		com.linkare.rec.data.MultiplierHelper.write(ostream, value.getMultiplier());
	}

}
