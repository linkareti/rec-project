package com.linkare.rec.data.synch;

public final class VTDateTimeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.DateTime value;

	public VTDateTimeHolder() {
	}

	public VTDateTimeHolder(final com.linkare.rec.data.synch.DateTime initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.VTDateTimeHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.VTDateTimeHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.VTDateTimeHelper.type();
	}

}
