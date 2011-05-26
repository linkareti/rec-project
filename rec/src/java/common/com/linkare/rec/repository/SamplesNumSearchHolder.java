package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class SamplesNumSearchHolder implements Streamable {
	public SamplesNumSearch value = null;

	public SamplesNumSearchHolder() {
	}

	public SamplesNumSearchHolder(final SamplesNumSearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = SamplesNumSearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		SamplesNumSearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return SamplesNumSearchHelper.type();
	}

}
