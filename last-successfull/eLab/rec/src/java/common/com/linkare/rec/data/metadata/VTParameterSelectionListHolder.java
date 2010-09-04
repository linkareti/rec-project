package com.linkare.rec.data.metadata;

public final class VTParameterSelectionListHolder implements org.omg.CORBA.portable.Streamable {
	public String[] value;

	public VTParameterSelectionListHolder() {
	}

	public VTParameterSelectionListHolder(String[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTParameterSelectionListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTParameterSelectionListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTParameterSelectionListHelper.type();
	}

}
