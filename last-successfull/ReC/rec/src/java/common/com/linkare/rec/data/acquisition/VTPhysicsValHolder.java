package com.linkare.rec.data.acquisition;

/**
 * com/linkare/rec/data/acquisition/VTPhysicsValHolder.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * D:/Projects/Java/SourceSafe/REC/IdlCompile/ReC6.idl Terca-feira, 24 de Junho
 * de 2003 11H02m BST
 */

// PhysicsVal is one of this base types... Complex types transferred as ByteList
public final class VTPhysicsValHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.PhysicsVal value;

	public VTPhysicsValHolder() {
	}

	public VTPhysicsValHolder(final com.linkare.rec.data.acquisition.PhysicsVal initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.VTPhysicsValHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.VTPhysicsValHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.VTPhysicsValHelper.type();
	}

}