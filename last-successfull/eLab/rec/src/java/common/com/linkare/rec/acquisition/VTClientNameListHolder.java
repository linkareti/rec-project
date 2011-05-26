package com.linkare.rec.acquisition;

public final class VTClientNameListHolder implements org.omg.CORBA.portable.Streamable {
	public String[] value;

	public VTClientNameListHolder() {
	}

	public VTClientNameListHolder(final String[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.VTClientNameListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.VTClientNameListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.VTClientNameListHelper.type();
	}

}
