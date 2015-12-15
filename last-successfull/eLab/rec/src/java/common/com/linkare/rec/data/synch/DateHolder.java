package com.linkare.rec.data.synch;

public final class DateHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.Date value = null;

	public DateHolder() {
	}

	public DateHolder(final com.linkare.rec.data.synch.Date initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.DateHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.DateHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.DateHelper.type();
	}

}
