package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class ChannelAcquisitionConfigSearchHolder implements Streamable {
	public ChannelAcquisitionConfigSearch value = null;

	public ChannelAcquisitionConfigSearchHolder() {
	}

	public ChannelAcquisitionConfigSearchHolder(final ChannelAcquisitionConfigSearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = ChannelAcquisitionConfigSearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		ChannelAcquisitionConfigSearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return ChannelAcquisitionConfigSearchHelper.type();
	}

}
