package com.linkare.rec.data.metadata;

public final class ChannelInfoListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ChannelInfo value[] = null;

	public ChannelInfoListHolder() {
	}

	public ChannelInfoListHolder(final com.linkare.rec.data.metadata.ChannelInfo[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ChannelInfoListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ChannelInfoListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ChannelInfoListHelper.type();
	}

}
