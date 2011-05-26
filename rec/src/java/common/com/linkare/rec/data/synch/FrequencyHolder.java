package com.linkare.rec.data.synch;

public final class FrequencyHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.Frequency value = null;

	public FrequencyHolder() {
	}

	public FrequencyHolder(final com.linkare.rec.data.synch.Frequency initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.FrequencyHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.FrequencyHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.FrequencyHelper.type();
	}

}
