package com.linkare.rec.data.acquisition;

public final class ByteArrayValueHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.ByteArrayValue value = null;

	public ByteArrayValueHolder() {
	}

	public ByteArrayValueHolder(final com.linkare.rec.data.acquisition.ByteArrayValue initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.ByteArrayValueHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.ByteArrayValueHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.ByteArrayValueHelper.type();
	}

}
