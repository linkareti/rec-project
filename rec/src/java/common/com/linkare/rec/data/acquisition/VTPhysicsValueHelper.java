package com.linkare.rec.data.acquisition;

public final class VTPhysicsValueHelper implements org.omg.CORBA.portable.BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/VTPhysicsValue:1.0";

	private static VTPhysicsValueHelper _instance = new VTPhysicsValueHelper();

	public VTPhysicsValueHelper() {
	}

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.acquisition.PhysicsValue that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTPhysicsValueHelper.type());
		VTPhysicsValueHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTPhysicsValueHelper.type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue extract(final org.omg.CORBA.Any a) {
		return VTPhysicsValueHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (VTPhysicsValueHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (VTPhysicsValueHelper.__typeCode == null) {
					if (VTPhysicsValueHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(VTPhysicsValueHelper._id);
					}
					VTPhysicsValueHelper.__active = true;
					VTPhysicsValueHelper.__typeCode = com.linkare.rec.data.acquisition.PhysicsValueHelper.type();
					VTPhysicsValueHelper.__typeCode = org.omg.CORBA.ORB.init().create_value_box_tc(
							VTPhysicsValueHelper._id, "VTPhysicsValue", VTPhysicsValueHelper.__typeCode);
					VTPhysicsValueHelper.__active = false;
				}
			}
		}
		return VTPhysicsValueHelper.__typeCode;
	}

	public static String id() {
		return VTPhysicsValueHelper._id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsValue read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof org.omg.CORBA_2_3.portable.InputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		return (com.linkare.rec.data.acquisition.PhysicsValue) ((org.omg.CORBA_2_3.portable.InputStream) istream)
				.read_value(VTPhysicsValueHelper._instance);
	}

	@Override
	public java.io.Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.data.acquisition.PhysicsValue tmp = com.linkare.rec.data.acquisition.PhysicsValueHelper
				.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.acquisition.PhysicsValue value) {
		if (!(ostream instanceof org.omg.CORBA_2_3.portable.OutputStream)) {
			throw new org.omg.CORBA.BAD_PARAM();
		}
		((org.omg.CORBA_2_3.portable.OutputStream) ostream).write_value(value, VTPhysicsValueHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final java.io.Serializable value) {
		if (!(value instanceof com.linkare.rec.data.acquisition.PhysicsValue)) {
			throw new org.omg.CORBA.MARSHAL();
		}
		final com.linkare.rec.data.acquisition.PhysicsValue valueType = (com.linkare.rec.data.acquisition.PhysicsValue) value;
		com.linkare.rec.data.acquisition.PhysicsValueHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTPhysicsValueHelper._id;
	}

}
