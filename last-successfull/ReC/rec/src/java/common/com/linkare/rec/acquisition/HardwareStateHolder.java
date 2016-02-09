package com.linkare.rec.acquisition;

public final class HardwareStateHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.HardwareState value = null;

	public HardwareStateHolder() {
	}

	public HardwareStateHolder(final com.linkare.rec.acquisition.HardwareState initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.HardwareStateHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.HardwareStateHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.HardwareStateHelper.type();
	}

}
