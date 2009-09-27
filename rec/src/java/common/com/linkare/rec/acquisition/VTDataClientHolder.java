package com.linkare.rec.acquisition;

public final class VTDataClientHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.DataClient value;

	public VTDataClientHolder() {
	}

	public VTDataClientHolder(com.linkare.rec.acquisition.DataClient initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.VTDataClientHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.VTDataClientHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.VTDataClientHelper.type();
	}

}
