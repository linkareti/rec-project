package com.linkare.rec.data.config;

public final class VTHardwareParameterConfigListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.config.ParameterConfig[] value;

	public VTHardwareParameterConfigListHolder() {
	}

	public VTHardwareParameterConfigListHolder(final com.linkare.rec.data.config.ParameterConfig[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.config.VTHardwareParameterConfigListHelper.type();
	}

}
