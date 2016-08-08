package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class RepositoryManagerHolder implements Streamable {
	public RepositoryManager value = null;

	public RepositoryManagerHolder() {
	}

	public RepositoryManagerHolder(final RepositoryManager initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = RepositoryManagerHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		RepositoryManagerHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return RepositoryManagerHelper.type();
	}

}
