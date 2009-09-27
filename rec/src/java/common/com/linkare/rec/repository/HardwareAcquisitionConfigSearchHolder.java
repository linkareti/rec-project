package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class HardwareAcquisitionConfigSearchHolder implements Streamable {
	public HardwareAcquisitionConfigSearch value = null;

	public HardwareAcquisitionConfigSearchHolder() {
	}

	public HardwareAcquisitionConfigSearchHolder(HardwareAcquisitionConfigSearch initialValue) {
		value = initialValue;
	}

	public void _read(InputStream i) {
		value = HardwareAcquisitionConfigSearchHelper.read(i);
	}

	public void _write(OutputStream o) {
		HardwareAcquisitionConfigSearchHelper.write(o, value);
	}

	public TypeCode _type() {
		return HardwareAcquisitionConfigSearchHelper.type();
	}

}
