package com.linkare.rec.data.synch;

public final class TimeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.Time value = null;

	public TimeHolder() {
	}

	public TimeHolder(final com.linkare.rec.data.synch.Time initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.TimeHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.TimeHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.TimeHelper.type();
	}

}
