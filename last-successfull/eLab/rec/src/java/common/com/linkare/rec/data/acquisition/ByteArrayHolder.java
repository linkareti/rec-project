package com.linkare.rec.data.acquisition;

public final class ByteArrayHolder implements org.omg.CORBA.portable.Streamable {
	public byte value[] = null;

	public ByteArrayHolder() {
	}

	public ByteArrayHolder(final byte[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.ByteArrayHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.ByteArrayHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.ByteArrayHelper.type();
	}

}
