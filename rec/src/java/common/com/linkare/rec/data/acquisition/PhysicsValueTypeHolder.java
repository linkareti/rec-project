package com.linkare.rec.data.acquisition;

public final class PhysicsValueTypeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsValueType value = null;

	public PhysicsValueTypeHolder() {
	}

	public PhysicsValueTypeHolder(com.linkare.rec.data.acquisition.PhysicsValueType initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.type();
	}

}
