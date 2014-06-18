package com.linkare.rec.data.metadata;

public final class VTHardwareParameterListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ChannelParameter[] value;

	public VTHardwareParameterListHolder() {
	}

	public VTHardwareParameterListHolder(final com.linkare.rec.data.metadata.ChannelParameter[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTHardwareParameterListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTHardwareParameterListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTHardwareParameterListHelper.type();
	}

}
