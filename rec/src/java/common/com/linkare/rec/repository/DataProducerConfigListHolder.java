package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class DataProducerConfigListHolder implements Streamable {
	public DataProducerConfig value[] = null;

	public DataProducerConfigListHolder() {
	}

	public DataProducerConfigListHolder(final DataProducerConfig[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = DataProducerConfigListHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		DataProducerConfigListHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return DataProducerConfigListHelper.type();
	}

}
