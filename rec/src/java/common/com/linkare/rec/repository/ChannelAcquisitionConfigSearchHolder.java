package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ChannelAcquisitionConfigSearchHolder implements Streamable {
	public ChannelAcquisitionConfigSearch value = null;

	public ChannelAcquisitionConfigSearchHolder() {
	}

	public ChannelAcquisitionConfigSearchHolder(ChannelAcquisitionConfigSearch initialValue) {
		value = initialValue;
	}

	public void _read(InputStream i) {
		value = ChannelAcquisitionConfigSearchHelper.read(i);
	}

	public void _write(OutputStream o) {
		ChannelAcquisitionConfigSearchHelper.write(o, value);
	}

	public TypeCode _type() {
		return ChannelAcquisitionConfigSearchHelper.type();
	}

}
