package com.linkare.rec.data.metadata;

public final class ParameterSelectionListHolder implements org.omg.CORBA.portable.Streamable {
	public String value[] = null;

	public ParameterSelectionListHolder() {
	}

	public ParameterSelectionListHolder(String[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.ParameterSelectionListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.ParameterSelectionListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.ParameterSelectionListHelper.type();
	}

}
