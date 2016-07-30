package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTSamplesNumSearchHolder implements Streamable {
	public SamplesNumSearch value;

	public VTSamplesNumSearchHolder() {
	}

	public VTSamplesNumSearchHolder(final SamplesNumSearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTSamplesNumSearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		VTSamplesNumSearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return VTSamplesNumSearchHelper.type();
	}

}
