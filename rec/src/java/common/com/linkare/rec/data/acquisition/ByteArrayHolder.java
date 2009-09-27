package com.linkare.rec.data.acquisition;

public final class ByteArrayHolder implements org.omg.CORBA.portable.Streamable {
	public byte value[] = null;

	public ByteArrayHolder() {
	}

	public ByteArrayHolder(byte[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.ByteArrayHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.ByteArrayHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.ByteArrayHelper.type();
	}

}
