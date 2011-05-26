package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTChannelNameHolder implements Streamable {
	public String value;

	public VTChannelNameHolder() {
	}

	public VTChannelNameHolder(final String initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTChannelNameHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		VTChannelNameHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return VTChannelNameHelper.type();
	}

}
