package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DataProducerConfigHolder implements Streamable {
	public DataProducerConfig value = null;

	public DataProducerConfigHolder() {
	}

	public DataProducerConfigHolder(final DataProducerConfig initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = DataProducerConfigHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		DataProducerConfigHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return DataProducerConfigHelper.type();
	}

}
