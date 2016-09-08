package com.linkare.rec.data.metadata;

public final class ChannelDirectionHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ChannelDirection value = null;

	public ChannelDirectionHolder() {
	}

	public ChannelDirectionHolder(final com.linkare.rec.data.metadata.ChannelDirection initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ChannelDirectionHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ChannelDirectionHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ChannelDirectionHelper.type();
	}

}
