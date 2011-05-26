package com.linkare.rec.data.metadata;

public final class ScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.Scale value = null;

	public ScaleHolder() {
	}

	public ScaleHolder(final com.linkare.rec.data.metadata.Scale initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ScaleHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ScaleHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ScaleHelper.type();
	}

}
