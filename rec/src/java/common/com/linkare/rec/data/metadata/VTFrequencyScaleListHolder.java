package com.linkare.rec.data.metadata;

public final class VTFrequencyScaleListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.FrequencyScale[] value;

	public VTFrequencyScaleListHolder() {
	}

	public VTFrequencyScaleListHolder(final com.linkare.rec.data.metadata.FrequencyScale[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.type();
	}

}
