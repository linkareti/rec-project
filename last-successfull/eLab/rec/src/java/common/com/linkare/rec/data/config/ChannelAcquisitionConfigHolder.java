package com.linkare.rec.data.config;

public final class ChannelAcquisitionConfigHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.config.ChannelAcquisitionConfig value = null;

	public ChannelAcquisitionConfigHolder() {
	}

	public ChannelAcquisitionConfigHolder(final com.linkare.rec.data.config.ChannelAcquisitionConfig initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.config.ChannelAcquisitionConfigHelper.type();
	}

}
