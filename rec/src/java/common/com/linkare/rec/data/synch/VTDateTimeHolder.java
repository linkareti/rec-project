package com.linkare.rec.data.synch;

public final class VTDateTimeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.DateTime value;

	public VTDateTimeHolder() {
	}

	public VTDateTimeHolder(com.linkare.rec.data.synch.DateTime initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.VTDateTimeHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.VTDateTimeHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.VTDateTimeHelper.type();
	}

}
