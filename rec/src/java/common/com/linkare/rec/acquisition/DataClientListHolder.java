package com.linkare.rec.acquisition;

/** CORBA Helper class */
public final class DataClientListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.DataClient value[] = null;

	public DataClientListHolder() {
	}

	public DataClientListHolder(final com.linkare.rec.acquisition.DataClient[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.DataClientListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.DataClientListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.DataClientListHelper.type();
	}

}
