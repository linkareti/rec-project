package com.linkare.rec.data.config;

public final class ParameterConfigListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.config.ParameterConfig value[] = null;

	public ParameterConfigListHolder() {
	}

	public ParameterConfigListHolder(final com.linkare.rec.data.config.ParameterConfig[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.config.ParameterConfigListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.config.ParameterConfigListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.config.ParameterConfigListHelper.type();
	}

}
