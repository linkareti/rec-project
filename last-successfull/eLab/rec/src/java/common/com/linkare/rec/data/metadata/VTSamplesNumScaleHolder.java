package com.linkare.rec.data.metadata;

public final class VTSamplesNumScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.SamplesNumScale value;

	public VTSamplesNumScaleHolder() {
	}

	public VTSamplesNumScaleHolder(final com.linkare.rec.data.metadata.SamplesNumScale initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.type();
	}

}
