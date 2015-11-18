package com.linkare.rec.data.metadata;

public final class HardwareInfoHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.HardwareInfo value = null;

	public HardwareInfoHolder() {
	}

	public HardwareInfoHolder(final com.linkare.rec.data.metadata.HardwareInfo initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.HardwareInfoHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.HardwareInfoHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.HardwareInfoHelper.type();
	}

}
