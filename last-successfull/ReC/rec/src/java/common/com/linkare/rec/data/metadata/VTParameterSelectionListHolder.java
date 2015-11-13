package com.linkare.rec.data.metadata;

public final class VTParameterSelectionListHolder implements org.omg.CORBA.portable.Streamable {
	public String[] value;

	public VTParameterSelectionListHolder() {
	}

	public VTParameterSelectionListHolder(final String[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTParameterSelectionListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTParameterSelectionListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTParameterSelectionListHelper.type();
	}

}
