package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DateTimeSearchHolder implements Streamable {
	public DateTimeSearch value = null;

	public DateTimeSearchHolder() {
	}

	public DateTimeSearchHolder(DateTimeSearch initialValue) {
		value = initialValue;
	}

	public void _read(InputStream i) {
		value = DateTimeSearchHelper.read(i);
	}

	public void _write(OutputStream o) {
		DateTimeSearchHelper.write(o, value);
	}

	public TypeCode _type() {
		return DateTimeSearchHelper.type();
	}

}
