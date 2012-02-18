package com.linkare.rec.data.synch;

public final class FrequencyDefTypeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.FrequencyDefType value = null;

	public FrequencyDefTypeHolder() {
	}

	public FrequencyDefTypeHolder(final com.linkare.rec.data.synch.FrequencyDefType initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.FrequencyDefTypeHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.FrequencyDefTypeHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.FrequencyDefTypeHelper.type();
	}

}
