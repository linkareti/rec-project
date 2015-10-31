package com.linkare.rec.data.acquisition;

abstract public class PhysicsValueListHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/PhysicsValueList:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.acquisition.PhysicsValue[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(PhysicsValueListHelper.type());
		PhysicsValueListHelper.write(out, that);
		a.read_value(out.create_input_stream(), PhysicsValueListHelper.type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue[] extract(final org.omg.CORBA.Any a) {
		return PhysicsValueListHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (PhysicsValueListHelper.__typeCode == null) {
			PhysicsValueListHelper.__typeCode = com.linkare.rec.data.acquisition.VTPhysicsValueHelper.type();
			PhysicsValueListHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
					PhysicsValueListHelper.__typeCode);
			PhysicsValueListHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
					com.linkare.rec.data.acquisition.PhysicsValueListHelper.id(), "PhysicsValueList",
					PhysicsValueListHelper.__typeCode);
		}
		return PhysicsValueListHelper.__typeCode;
	}

	public static String id() {
		return PhysicsValueListHelper._id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue[] read(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.acquisition.PhysicsValue value[] = null;
		final int _len0 = istream.read_long();
		value = new com.linkare.rec.data.acquisition.PhysicsValue[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = com.linkare.rec.data.acquisition.VTPhysicsValueHelper.read(istream);
		}
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.acquisition.PhysicsValue[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			com.linkare.rec.data.acquisition.VTPhysicsValueHelper.write(ostream, value[_i0]);
		}
	}

}
