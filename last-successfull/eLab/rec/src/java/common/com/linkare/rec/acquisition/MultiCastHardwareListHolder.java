package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/MultiCastHardwareListHolder.java . Generated by
 * the IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

// A proxy controller for each hardware running now...
public final class MultiCastHardwareListHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.MultiCastHardware value[] = null;

	public MultiCastHardwareListHolder() {
	}

	public MultiCastHardwareListHolder(final com.linkare.rec.acquisition.MultiCastHardware[] initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.MultiCastHardwareListHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.MultiCastHardwareListHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.MultiCastHardwareListHelper.type();
	}

}
