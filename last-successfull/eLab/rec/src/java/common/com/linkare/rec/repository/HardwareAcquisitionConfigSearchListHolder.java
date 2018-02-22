package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HardwareAcquisitionConfigSearchListHolder implements Streamable {
	public HardwareAcquisitionConfigSearch value[] = null;

	public HardwareAcquisitionConfigSearchListHolder() {
	}

	public HardwareAcquisitionConfigSearchListHolder(final HardwareAcquisitionConfigSearch[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = HardwareAcquisitionConfigSearchListHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		HardwareAcquisitionConfigSearchListHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return HardwareAcquisitionConfigSearchListHelper.type();
	}

}
