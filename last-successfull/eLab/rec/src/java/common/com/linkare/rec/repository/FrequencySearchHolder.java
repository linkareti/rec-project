package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class FrequencySearchHolder implements Streamable {
	public FrequencySearch value = null;

	public FrequencySearchHolder() {
	}

	public FrequencySearchHolder(final FrequencySearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = FrequencySearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		FrequencySearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return FrequencySearchHelper.type();
	}

}
