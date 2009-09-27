package com.linkare.rec.data.acquisition;

public final class VTPhysicsValueMatrixHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsValue[][] value;

	public VTPhysicsValueMatrixHolder() {
	}

	public VTPhysicsValueMatrixHolder(com.linkare.rec.data.acquisition.PhysicsValue[][] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.VTPhysicsValueMatrixHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.VTPhysicsValueMatrixHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.VTPhysicsValueMatrixHelper.type();
	}

}
