package com.linkare.rec.data.config;

public final class VTChannelParameterConfigListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.config.ParameterConfig[] value;

	public VTChannelParameterConfigListHolder() {
	}

	public VTChannelParameterConfigListHolder(final com.linkare.rec.data.config.ParameterConfig[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.config.VTChannelParameterConfigListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.config.VTChannelParameterConfigListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.config.VTChannelParameterConfigListHelper.type();
	}

}
