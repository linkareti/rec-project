package com.linkare.rec.data;

public final class MultiplierHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.Multiplier value = null;

	public MultiplierHolder() {
	}

	public MultiplierHolder(final com.linkare.rec.data.Multiplier initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.MultiplierHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.MultiplierHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.MultiplierHelper.type();
	}

}
