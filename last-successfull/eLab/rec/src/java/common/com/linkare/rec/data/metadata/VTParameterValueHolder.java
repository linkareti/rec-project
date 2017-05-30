package com.linkare.rec.data.metadata;

public final class VTParameterValueHolder implements org.omg.CORBA.portable.Streamable {
	public String value;

	public VTParameterValueHolder() {
	}

	public VTParameterValueHolder(final String initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTParameterValueHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTParameterValueHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTParameterValueHelper.type();
	}

}
