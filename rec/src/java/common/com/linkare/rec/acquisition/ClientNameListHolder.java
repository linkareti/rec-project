package com.linkare.rec.acquisition;

/** CORBA Helper class */
public final class ClientNameListHolder implements org.omg.CORBA.portable.Streamable {
	public String value[] = null;

	public ClientNameListHolder() {
	}

	public ClientNameListHolder(String[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.ClientNameListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.ClientNameListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.ClientNameListHelper.type();
	}

}
