package com.linkare.rec.data.acquisition;

abstract public class PhysicsValueTypeHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/PhysicsValueType:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.acquisition.PhysicsValueType that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsValueType extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			__typeCode = org.omg.CORBA.ORB.init().create_enum_tc(
					com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.id(),
					"PhysicsValueType",
					new String[] { "BooleanVal", "ByteVal", "ShortVal", "IntVal", "LongVal", "FloatVal", "DoubleVal",
							"ByteArrayVal" });
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsValueType read(org.omg.CORBA.portable.InputStream istream) {
		return com.linkare.rec.data.acquisition.PhysicsValueType.from_int(istream.read_long());
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.data.acquisition.PhysicsValueType value) {
		ostream.write_long(value.value());
	}

}
