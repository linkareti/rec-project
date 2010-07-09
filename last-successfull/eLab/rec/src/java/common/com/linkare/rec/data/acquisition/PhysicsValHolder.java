package com.linkare.rec.data.acquisition;

public final class PhysicsValHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsVal value = null;

	public PhysicsValHolder() {
	}

	public PhysicsValHolder(com.linkare.rec.data.acquisition.PhysicsVal initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.PhysicsValHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.PhysicsValHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.PhysicsValHelper.type();
	}

}
