package com.linkare.rec.data.metadata;

public final class ChannelDirectionHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ChannelDirection value = null;

	public ChannelDirectionHolder() {
	}

	public ChannelDirectionHolder(com.linkare.rec.data.metadata.ChannelDirection initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ChannelDirectionHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ChannelDirectionHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ChannelDirectionHelper.type();
	}

}
