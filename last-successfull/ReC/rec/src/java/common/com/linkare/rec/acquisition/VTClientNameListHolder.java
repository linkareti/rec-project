package com.linkare.rec.acquisition;

public final class VTClientNameListHolder implements org.omg.CORBA.portable.Streamable {
	public String[] value;

	public VTClientNameListHolder() {
	}

	public VTClientNameListHolder(String[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.VTClientNameListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.VTClientNameListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.VTClientNameListHelper.type();
	}

}
