package com.linkare.rec.data.config;

public final class ParameterConfigHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.config.ParameterConfig value = null;

	public ParameterConfigHolder() {
	}

	public ParameterConfigHolder(com.linkare.rec.data.config.ParameterConfig initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.config.ParameterConfigHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.config.ParameterConfigHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.config.ParameterConfigHelper.type();
	}

}
