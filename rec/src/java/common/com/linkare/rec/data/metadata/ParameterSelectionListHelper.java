package com.linkare.rec.data.metadata;

abstract public class ParameterSelectionListHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ParameterSelectionList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final String[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ParameterSelectionListHelper.type());
		ParameterSelectionListHelper.write(out, that);
		a.read_value(out.create_input_stream(), ParameterSelectionListHelper.type());
	}

	public static String[] extract(final org.omg.CORBA.Any a) {
		return ParameterSelectionListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ParameterSelectionListHelper.__typeCode == null) {
			ParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_wstring_tc(0);
			ParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ParameterValueHelper.id(), "ParameterValue",
					ParameterSelectionListHelper.__typeCode);
			ParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					ParameterSelectionListHelper.__typeCode);
			ParameterSelectionListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.metadata.ParameterSelectionListHelper.id(), "ParameterSelectionList",
					ParameterSelectionListHelper.__typeCode);
		}
		return ParameterSelectionListHelper.__typeCode;
	}

	public static String id() {
		return ParameterSelectionListHelper._id;
	}

	public static String[] read(final org.omg.CORBA.portable.InputStream istream) {
		String value[] = null;
		final int _len0 = istream.read_long();
		value = new String[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.metadata.ParameterValueHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final String[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.metadata.ParameterValueHelper.write(ostream, value[_i0]);
		}
	}

}
