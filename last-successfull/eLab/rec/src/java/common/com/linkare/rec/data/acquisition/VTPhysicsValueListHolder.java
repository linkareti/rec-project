package com.linkare.rec.data.acquisition;

public final class VTPhysicsValueListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsValue[] value;

	public VTPhysicsValueListHolder() {
	}

	public VTPhysicsValueListHolder(final com.linkare.rec.data.acquisition.PhysicsValue[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.VTPhysicsValueListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.VTPhysicsValueListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.VTPhysicsValueListHelper.type();
	}

}
