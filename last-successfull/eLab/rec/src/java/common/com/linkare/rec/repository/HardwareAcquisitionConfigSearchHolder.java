package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HardwareAcquisitionConfigSearchHolder implements Streamable {
	public HardwareAcquisitionConfigSearch value = null;

	public HardwareAcquisitionConfigSearchHolder() {
	}

	public HardwareAcquisitionConfigSearchHolder(final HardwareAcquisitionConfigSearch initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = HardwareAcquisitionConfigSearchHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		HardwareAcquisitionConfigSearchHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return HardwareAcquisitionConfigSearchHelper.type();
	}

}
