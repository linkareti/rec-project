package com.linkare.rec.data.metadata;

public final class VTScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.Scale value;

	public VTScaleHolder() {
	}

	public VTScaleHolder(com.linkare.rec.data.metadata.Scale initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTScaleHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTScaleHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTScaleHelper.type();
	}

}
