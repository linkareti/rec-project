package com.linkare.rec.data.acquisition;

public final class PhysicsValHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsVal value = null;

	public PhysicsValHolder() {
	}

	public PhysicsValHolder(final com.linkare.rec.data.acquisition.PhysicsVal initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.PhysicsValHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.PhysicsValHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.PhysicsValHelper.type();
	}

}
