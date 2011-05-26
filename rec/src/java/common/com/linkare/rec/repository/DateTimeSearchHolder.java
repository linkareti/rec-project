package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DateTimeSearchHolder implements Streamable {
	public DateTimeSearch value = null;

	public DateTimeSearchHolder() {
	}

	public DateTimeSearchHolder(final DateTimeSearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = DateTimeSearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		DateTimeSearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return DateTimeSearchHelper.type();
	}

}
