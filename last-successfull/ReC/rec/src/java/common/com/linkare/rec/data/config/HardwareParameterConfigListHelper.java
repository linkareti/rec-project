package com.linkare.rec.data.config;

abstract public class HardwareParameterConfigListHelper {
	private static String _id = "IDL:com/linkare/rec/data/config/HardwareParameterConfigList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.config.ParameterConfig[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(HardwareParameterConfigListHelper.type());
		HardwareParameterConfigListHelper.write(out, that);
		a.read_value(out.create_input_stream(), HardwareParameterConfigListHelper.type());
	}

	public static com.linkare.rec.data.config.ParameterConfig[] extract(final org.omg.CORBA.Any a) {
		return HardwareParameterConfigListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (HardwareParameterConfigListHelper.__typeCode == null) {
			HardwareParameterConfigListHelper.__typeCode = com.linkare.rec.data.config.ParameterConfigHelper.type();
			HardwareParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					HardwareParameterConfigListHelper.__typeCode);
			HardwareParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.config.ParameterConfigListHelper.id(), "ParameterConfigList",
					HardwareParameterConfigListHelper.__typeCode);
			HardwareParameterConfigListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.config.HardwareParameterConfigListHelper.id(), "HardwareParameterConfigList",
					HardwareParameterConfigListHelper.__typeCode);
		}
		return HardwareParameterConfigListHelper.__typeCode;
	}

	public static String id() {
		return HardwareParameterConfigListHelper._id;
	}

	public static com.linkare.rec.data.config.ParameterConfig[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.config.ParameterConfig value[] = null;
		value = com.linkare.rec.data.config.ParameterConfigListHelper.read(istream);
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.config.ParameterConfig[] value) {
		com.linkare.rec.data.config.ParameterConfigListHelper.write(ostream, value);
	}

}
