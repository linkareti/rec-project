package com.linkare.rec.data.metadata;

public final class ChannelParameterHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ChannelParameter value = null;

	public ChannelParameterHolder() {
	}

	public ChannelParameterHolder(final com.linkare.rec.data.metadata.ChannelParameter initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ChannelParameterHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ChannelParameterHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ChannelParameterHelper.type();
	}

}
