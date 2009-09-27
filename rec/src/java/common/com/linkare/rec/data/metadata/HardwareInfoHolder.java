package com.linkare.rec.data.metadata;

public final class HardwareInfoHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.HardwareInfo value = null;

	public HardwareInfoHolder() {
	}

	public HardwareInfoHolder(com.linkare.rec.data.metadata.HardwareInfo initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.HardwareInfoHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.HardwareInfoHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.HardwareInfoHelper.type();
	}

}
