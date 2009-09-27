package com.linkare.rec.data.synch;

public final class DateHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.Date value = null;

	public DateHolder() {
	}

	public DateHolder(com.linkare.rec.data.synch.Date initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.DateHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.DateHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.DateHelper.type();
	}

}
