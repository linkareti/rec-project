package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTChannelNameHolder implements Streamable {
	public String value;

	public VTChannelNameHolder() {
	}

	public VTChannelNameHolder(String initialValue) {
		value = initialValue;
	}

	public void _read(InputStream i) {
		value = VTChannelNameHelper.read(i);
	}

	public void _write(OutputStream o) {
		VTChannelNameHelper.write(o, value);
	}

	public TypeCode _type() {
		return VTChannelNameHelper.type();
	}

}
