package com.linkare.rec.data.metadata;

public final class FrequencyScaleHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.FrequencyScale value = null;

	public FrequencyScaleHolder() {
	}

	public FrequencyScaleHolder(final com.linkare.rec.data.metadata.FrequencyScale initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.FrequencyScaleHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.FrequencyScaleHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.FrequencyScaleHelper.type();
	}

}
