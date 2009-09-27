package com.linkare.rec.data.metadata;

public final class VTSamplesNumScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.SamplesNumScale value;

	public VTSamplesNumScaleHolder() {
	}

	public VTSamplesNumScaleHolder(com.linkare.rec.data.metadata.SamplesNumScale initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.type();
	}

}
