package com.linkare.rec.data.acquisition;

public final class SamplesPacketHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.SamplesPacket value = null;

	public SamplesPacketHolder() {
	}

	public SamplesPacketHolder(final com.linkare.rec.data.acquisition.SamplesPacket initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.SamplesPacketHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.SamplesPacketHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.SamplesPacketHelper.type();
	}

}
