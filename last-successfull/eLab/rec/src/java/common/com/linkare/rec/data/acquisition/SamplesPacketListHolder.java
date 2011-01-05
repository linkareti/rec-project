package com.linkare.rec.data.acquisition;

public final class SamplesPacketListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.acquisition.SamplesPacket value[] = null;

	public SamplesPacketListHolder() {
	}

	public SamplesPacketListHolder(com.linkare.rec.data.acquisition.SamplesPacket[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.acquisition.SamplesPacketListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.acquisition.SamplesPacketListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.acquisition.SamplesPacketListHelper.type();
	}

}
