package com.linkare.rec.data.metadata;

public final class VTScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.Scale value;

	public VTScaleHolder() {
	}

	public VTScaleHolder(final com.linkare.rec.data.metadata.Scale initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTScaleHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTScaleHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTScaleHelper.type();
	}

}
