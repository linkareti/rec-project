package com.linkare.rec.data.acquisition;

public final class VTPhysicsValueMatrixHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/VTPhysicsValueMatrix:1.0";

	private static VTPhysicsValueMatrixHelper _instance = new VTPhysicsValueMatrixHelper();

	public VTPhysicsValueMatrixHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.acquisition.PhysicsValue[][] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTPhysicsValueMatrixHelper.type());
		VTPhysicsValueMatrixHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTPhysicsValueMatrixHelper.type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue[][] extract(final org.omg.CORBA.Any a) {
		return VTPhysicsValueMatrixHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTPhysicsValueMatrixHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTPhysicsValueMatrixHelper.__typeCode == null) {
					if (VTPhysicsValueMatrixHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTPhysicsValueMatrixHelper._id);
					}
					VTPhysicsValueMatrixHelper.__active = true;
					VTPhysicsValueMatrixHelper.__typeCode = com.linkare.rec.data.acquisition.VTPhysicsValueListHelper
							.type();
					VTPhysicsValueMatrixHelper.__typeCode = org.omg.CORBA.ORB.init().create_sequence_tc(0,
							VTPhysicsValueMatrixHelper.__typeCode);
					VTPhysicsValueMatrixHelper.__typeCode = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.id(), "PhysicsValueMatrix",
							VTPhysicsValueMatrixHelper.__typeCode);
					VTPhysicsValueMatrixHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTPhysicsValueMatrixHelper._id, "VTPhysicsValueMatrix",
							VTPhysicsValueMatrixHelper.__typeCode);
					VTPhysicsValueMatrixHelper.__active = false;
				}
			}
		}
		return VTPhysicsValueMatrixHelper.__typeCode;
	}

	public static String id() {
		return VTPhysicsValueMatrixHelper._id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue[][] read(
			final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.acquisition.PhysicsValue[][]) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTPhysicsValueMatrixHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.acquisition.PhysicsValue[][] tmp;
		tmp = com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.acquisition.PhysicsValue[][] value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTPhysicsValueMatrixHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.acquisition.PhysicsValue[][])) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.acquisition.PhysicsValue[][] valueType = (com.linkare.rec.data.acquisition.PhysicsValue[][]) value;
		com.linkare.rec.data.acquisition.PhysicsValueMatrixHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTPhysicsValueMatrixHelper._id;
	}

}
