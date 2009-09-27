package com.linkare.rec.data.metadata;

public final class SamplesNumScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.SamplesNumScale value = null;

	public SamplesNumScaleHolder() {
	}

	public SamplesNumScaleHolder(com.linkare.rec.data.metadata.SamplesNumScale initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.SamplesNumScaleHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.SamplesNumScaleHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.SamplesNumScaleHelper.type();
	}

}
