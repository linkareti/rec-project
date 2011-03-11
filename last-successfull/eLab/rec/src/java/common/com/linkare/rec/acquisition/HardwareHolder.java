package com.linkare.rec.acquisition;

public final class HardwareHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.Hardware value = null;

	public HardwareHolder() {
	}

	public HardwareHolder(com.linkare.rec.acquisition.Hardware initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.HardwareHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.HardwareHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.HardwareHelper.type();
	}

}
