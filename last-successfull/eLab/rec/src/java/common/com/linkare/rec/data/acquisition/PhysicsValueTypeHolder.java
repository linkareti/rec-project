package com.linkare.rec.data.acquisition;

public final class PhysicsValueTypeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsValueType value = null;

	public PhysicsValueTypeHolder() {
	}

	public PhysicsValueTypeHolder(final com.linkare.rec.data.acquisition.PhysicsValueType initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.type();
	}

}
