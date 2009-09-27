package com.linkare.rec.data.acquisition;

public final class ByteArrayValueHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.ByteArrayValue value = null;

	public ByteArrayValueHolder() {
	}

	public ByteArrayValueHolder(com.linkare.rec.data.acquisition.ByteArrayValue initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.ByteArrayValueHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.ByteArrayValueHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.ByteArrayValueHelper.type();
	}

}
