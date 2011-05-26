package com.linkare.rec.acquisition;

public final class VTDataClientListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.DataClient[] value;

	public VTDataClientListHolder() {
	}

	public VTDataClientListHolder(final com.linkare.rec.acquisition.DataClient[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.VTDataClientListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.VTDataClientListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.VTDataClientListHelper.type();
	}

}
