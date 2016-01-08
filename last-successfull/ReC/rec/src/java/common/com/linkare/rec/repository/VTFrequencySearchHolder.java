package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTFrequencySearchHolder implements Streamable {
	public FrequencySearch value;

	public VTFrequencySearchHolder() {
	}

	public VTFrequencySearchHolder(final FrequencySearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTFrequencySearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		VTFrequencySearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return VTFrequencySearchHelper.type();
	}

}
