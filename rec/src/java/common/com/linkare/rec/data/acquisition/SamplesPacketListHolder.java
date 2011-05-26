package com.linkare.rec.data.acquisition;

public final class SamplesPacketListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.SamplesPacket value[] = null;

	public SamplesPacketListHolder() {
	}

	public SamplesPacketListHolder(final com.linkare.rec.data.acquisition.SamplesPacket[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.SamplesPacketListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.SamplesPacketListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.SamplesPacketListHelper.type();
	}

}
