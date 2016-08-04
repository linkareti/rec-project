package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTHardwareAcquisitionConfigSearchListHolder implements Streamable {
	public HardwareAcquisitionConfigSearch[] value;

	public VTHardwareAcquisitionConfigSearchListHolder() {
	}

	public VTHardwareAcquisitionConfigSearchListHolder(final HardwareAcquisitionConfigSearch[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final InputStream i) {
		value = VTHardwareAcquisitionConfigSearchListHelper.read(i);
	}

	@Override
	public void _write(final OutputStream o) {
		VTHardwareAcquisitionConfigSearchListHelper.write(o, value);
	}

	@Override
	public TypeCode _type() {
		return VTHardwareAcquisitionConfigSearchListHelper.type();
	}

}
