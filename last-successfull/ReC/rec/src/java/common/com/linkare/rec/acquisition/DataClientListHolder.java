package com.linkare.rec.acquisition;

/** CORBA Helper class */
public final class DataClientListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.DataClient value[] = null;

	public DataClientListHolder() {
	}

	public DataClientListHolder(com.linkare.rec.acquisition.DataClient[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.DataClientListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.DataClientListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.DataClientListHelper.type();
	}

}
