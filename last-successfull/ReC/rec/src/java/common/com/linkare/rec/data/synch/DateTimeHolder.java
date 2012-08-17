package com.linkare.rec.data.synch;

public final class DateTimeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.DateTime value = null;

	public DateTimeHolder() {
	}

	public DateTimeHolder(final com.linkare.rec.data.synch.DateTime initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.DateTimeHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.DateTimeHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.DateTimeHelper.type();
	}

}
