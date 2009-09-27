package com.linkare.rec.data.synch;

public final class DateTimeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.DateTime value = null;

	public DateTimeHolder() {
	}

	public DateTimeHolder(com.linkare.rec.data.synch.DateTime initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.DateTimeHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.DateTimeHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.DateTimeHelper.type();
	}

}
