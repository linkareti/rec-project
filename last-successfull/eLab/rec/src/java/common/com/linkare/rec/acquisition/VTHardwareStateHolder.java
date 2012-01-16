package com.linkare.rec.acquisition;

public final class VTHardwareStateHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.HardwareState value;

	public VTHardwareStateHolder() {
	}

	public VTHardwareStateHolder(final com.linkare.rec.acquisition.HardwareState initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.VTHardwareStateHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.VTHardwareStateHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.VTHardwareStateHelper.type();
	}

}
