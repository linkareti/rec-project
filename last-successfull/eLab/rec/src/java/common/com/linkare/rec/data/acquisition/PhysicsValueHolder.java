package com.linkare.rec.data.acquisition;

public final class PhysicsValueHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsValue value = null;

	public PhysicsValueHolder() {
	}

	public PhysicsValueHolder(final com.linkare.rec.data.acquisition.PhysicsValue initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.PhysicsValueHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.PhysicsValueHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.PhysicsValueHelper.type();
	}

}