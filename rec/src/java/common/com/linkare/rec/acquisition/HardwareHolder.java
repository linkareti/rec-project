package com.linkare.rec.acquisition;

public final class HardwareHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.Hardware value = null;

	public HardwareHolder() {
	}

	public HardwareHolder(final com.linkare.rec.acquisition.Hardware initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.HardwareHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.HardwareHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.HardwareHelper.type();
	}

}
