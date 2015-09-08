package com.linkare.rec.acquisition;

public final class VTMultiCastHardwareListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.MultiCastHardware[] value;

	public VTMultiCastHardwareListHolder() {
	}
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public VTMultiCastHardwareListHolder(final com.linkare.rec.acquisition.MultiCastHardware[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.type();
	}

}
