package com.linkare.rec.data.metadata;

public final class VTChannelParameterListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.data.metadata.ChannelParameter[] value;

	public VTChannelParameterListHolder() {
	}

	public VTChannelParameterListHolder(com.linkare.rec.data.metadata.ChannelParameter[] initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.data.metadata.VTChannelParameterListHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.data.metadata.VTChannelParameterListHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.data.metadata.VTChannelParameterListHelper.type();
	}

}
