package com.linkare.rec.data.acquisition;

abstract public class PhysicsValueTypeHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/PhysicsValueType:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.acquisition.PhysicsValueType that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(PhysicsValueTypeHelper.type());
		PhysicsValueTypeHelper.write(out, that);
		a.read_value(out.create_input_stream(), PhysicsValueTypeHelper.type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsValueType extract(final org.omg.CORBA.Any a) {
		return PhysicsValueTypeHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (PhysicsValueTypeHelper.__typeCode == null) {
			PhysicsValueTypeHelper.__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.id(),
					"PhysicsValueType",
					new String[] { "BooleanVal", "ByteVal", "ShortVal", "IntVal", "LongVal", "FloatVal", "DoubleVal",
							"ByteArrayVal" });
		}
		return PhysicsValueTypeHelper.__typeCode;
	}

	public static String id() {
		return PhysicsValueTypeHelper._id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsValueType read(
			final org.omg.CORBA.portable.InputStream istream) {
		return com.linkare.rec.data.acquisition.PhysicsValueType.from_int(istream.read_long());
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.acquisition.PhysicsValueType value) {
		ostream.write_long(value.value());
	}

}
