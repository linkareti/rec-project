package com.linkare.rec.data.metadata;

public final class ParameterSelectionListHolder implements org.omg.CORBA.portable.Streamable {
	public String value[] = null;

	public ParameterSelectionListHolder() {
	}

	public ParameterSelectionListHolder(final String[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ParameterSelectionListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ParameterSelectionListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ParameterSelectionListHelper.type();
	}

}
