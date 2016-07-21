package com.linkare.rec.data.config;

abstract public class ParameterConfigHelper {
	private static String _id = "IDL:com/linkare/rec/data/config/ParameterConfig:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.config.ParameterConfig that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ParameterConfigHelper.type());
		ParameterConfigHelper.write(out, that);
		a.read_value(out.create_input_stream(), ParameterConfigHelper.type());
	}

	public static com.linkare.rec.data.config.ParameterConfig extract(final org.omg.CORBA.Any a) {
		return ParameterConfigHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ParameterConfigHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (ParameterConfigHelper.__typeCode == null) {
					if (ParameterConfigHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(ParameterConfigHelper._id);
					}
					ParameterConfigHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[2];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ParameterNameHelper.id(), "ParameterName", _tcOf_members0);
					_members0[0] = new org.omg.CORBA.StructMember("parameter_name", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ParameterValueHelper.id(), "ParameterValue", _tcOf_members0);
					_members0[1] = new org.omg.CORBA.StructMember("parameter_value", _tcOf_members0, null);
					ParameterConfigHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.config.ParameterConfigHelper.id(), "ParameterConfig", _members0);
					ParameterConfigHelper.__active = false;
				}
			}
		}
		return ParameterConfigHelper.__typeCode;
	}

	public static String id() {
		return ParameterConfigHelper._id;
	}

	public static com.linkare.rec.data.config.ParameterConfig read(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.config.ParameterConfig new_one = new com.linkare.rec.data.config.ParameterConfig();

		new_one.setParameterName(com.linkare.rec.data.metadata.ParameterNameHelper.read(istream));
		new_one.setParameterValue(com.linkare.rec.data.metadata.ParameterValueHelper.read(istream));

		return new_one;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.config.ParameterConfig value) {
		com.linkare.rec.data.metadata.ParameterNameHelper.write(ostream, value.getParameterName());
		com.linkare.rec.data.metadata.ParameterValueHelper.write(ostream, value.getParameterValue());
	}

}
