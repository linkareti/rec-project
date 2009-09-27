package com.linkare.rec.data.synch;

public final class FrequencyDefTypeHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.synch.FrequencyDefType value = null;

	public FrequencyDefTypeHolder() {
	}

	public FrequencyDefTypeHolder(com.linkare.rec.data.synch.FrequencyDefType initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.synch.FrequencyDefTypeHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.synch.FrequencyDefTypeHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.synch.FrequencyDefTypeHelper.type();
	}

}
