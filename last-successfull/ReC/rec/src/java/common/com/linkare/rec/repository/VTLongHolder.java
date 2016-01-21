package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTLongHolder implements Streamable {
	public int value;

	public VTLongHolder() {
	}

	public VTLongHolder(final int initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTLongHelper.read(i).getValue();
	}

	@Override
	public void _write(final OutputStream o) {
		final VTLong vb = new VTLong(value);
		VTLongHelper.write(o, vb);
	}

	@Override
	public TypeCode _type() {
		return VTLongHelper.type();
	}

}
