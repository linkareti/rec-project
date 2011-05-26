package com.linkare.rec.acquisition;

/** CORBA Helper class */
public final class ClientNameListHolder implements org.omg.CORBA.portable.Streamable {
	public String value[] = null;

	public ClientNameListHolder() {
	}

	public ClientNameListHolder(final String[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.ClientNameListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.ClientNameListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.ClientNameListHelper.type();
	}

}
