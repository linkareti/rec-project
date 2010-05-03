package com.linkare.rec.data.metadata;

public final class ParameterTypeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ParameterType value = null;

	public ParameterTypeHolder() {
	}

	public ParameterTypeHolder(com.linkare.rec.data.metadata.ParameterType initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ParameterTypeHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ParameterTypeHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ParameterTypeHelper.type();
	}

}
