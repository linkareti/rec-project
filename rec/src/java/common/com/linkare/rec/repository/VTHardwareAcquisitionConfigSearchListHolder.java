package com.linkare.rec.repository;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class VTHardwareAcquisitionConfigSearchListHolder implements Streamable {
	public HardwareAcquisitionConfigSearch[] value;

	public VTHardwareAcquisitionConfigSearchListHolder() {
	}

	public VTHardwareAcquisitionConfigSearchListHolder(HardwareAcquisitionConfigSearch[] initialValue) {
		value = initialValue;
	}

	public void _read(InputStream i) {
		value = VTHardwareAcquisitionConfigSearchListHelper.read(i);
	}

	public void _write(OutputStream o) {
		VTHardwareAcquisitionConfigSearchListHelper.write(o, value);
	}

	public TypeCode _type() {
		return VTHardwareAcquisitionConfigSearchListHelper.type();
	}

}
