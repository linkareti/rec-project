package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTDateTimeSearchHolder implements Streamable {
	public DateTimeSearch value;

	public VTDateTimeSearchHolder() {
	}

	public VTDateTimeSearchHolder(final DateTimeSearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTDateTimeSearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		VTDateTimeSearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return VTDateTimeSearchHelper.type();
	}

}
