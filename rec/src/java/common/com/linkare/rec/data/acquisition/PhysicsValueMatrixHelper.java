package com.linkare.rec.data.acquisition;

abstract public class PhysicsValueMatrixHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/PhysicsValueMatrix:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.acquisition.PhysicsValue[][] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(PhysicsValueMatrixHelper.type());
		PhysicsValueMatrixHelper.write(out, that);
		a.read_value(out.create_input_stream(), PhysicsValueMatrixHelper.type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue[][] extract(final org.omg.CORBA.Any a) {
		return PhysicsValueMatrixHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (PhysicsValueMatrixHelper.__typeCode == null) {
			PhysicsValueMatrixHelper.__typeCode = com.linkare.rec.data.acquisition.VTPhysicsValueListHelper.type();
			PhysicsValueMatrixHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					PhysicsValueMatrixHelper.__typeCode);
			PhysicsValueMatrixHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.id(), "PhysicsValueMatrix",
					PhysicsValueMatrixHelper.__typeCode);
		}
		return PhysicsValueMatrixHelper.__typeCode;
	}

	public static String id() {
		return PhysicsValueMatrixHelper._id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue[][] read(
			final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.acquisition.PhysicsValue[] value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.acquisition.PhysicsValue[_len0][];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.acquisition.VTPhysicsValueListHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.acquisition.PhysicsValue[][] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.acquisition.VTPhysicsValueListHelper.write(ostream, value[_i0]);
		}
	}

}
