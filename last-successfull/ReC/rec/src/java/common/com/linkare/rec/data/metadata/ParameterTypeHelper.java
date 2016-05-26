package com.linkare.rec.data.metadata;

abstract public class ParameterTypeHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ParameterType:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ParameterType that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ParameterTypeHelper.type());
		ParameterTypeHelper.write(out, that);
		a.read_value(out.create_input_stream(), ParameterTypeHelper.type());
	}

	public static com.linkare.rec.data.metadata.ParameterType extract(final org.omg.CORBA.Any a) {
		return ParameterTypeHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ParameterTypeHelper.__typeCode == null) {
			ParameterTypeHelper.__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.data.metadata.ParameterTypeHelper.id(), "ParameterType",
					new String[] { "SelectionListValue", "ContinuousValue", "OnOffValue" });
		}
		return ParameterTypeHelper.__typeCode;
	}

	public static String id() {
		return ParameterTypeHelper._id;
	}

	public static com.linkare.rec.data.metadata.ParameterType read(final org.omg.CORBA.portable.InputStream istream) {
		return new ParameterType(istream.read_long());
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ParameterType value) {
		ostream.write_long(value.getValue());
	}

}
