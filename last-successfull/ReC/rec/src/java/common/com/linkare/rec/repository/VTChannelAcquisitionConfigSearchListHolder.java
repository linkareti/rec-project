package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTChannelAcquisitionConfigSearchListHolder implements Streamable {
	public ChannelAcquisitionConfigSearch[] value;

	public VTChannelAcquisitionConfigSearchListHolder() {
	}

	public VTChannelAcquisitionConfigSearchListHolder(final ChannelAcquisitionConfigSearch[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTChannelAcquisitionConfigSearchListHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		VTChannelAcquisitionConfigSearchListHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return VTChannelAcquisitionConfigSearchListHelper.type();
	}

}
