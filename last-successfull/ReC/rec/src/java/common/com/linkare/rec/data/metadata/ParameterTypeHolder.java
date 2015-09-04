package com.linkare.rec.data.metadata;

public final class ParameterTypeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ParameterType value = null;

	public ParameterTypeHolder() {
	}

	public ParameterTypeHolder(final com.linkare.rec.data.metadata.ParameterType initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ParameterTypeHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ParameterTypeHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ParameterTypeHelper.type();
	}

}
