package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/MultiCastHardwareHolder.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

// if you get here in a middle of1 an acquisition call getAllSamplesUntilNow
public final class MultiCastHardwareHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.MultiCastHardware value = null;

	public MultiCastHardwareHolder() {
	}

	public MultiCastHardwareHolder(final com.linkare.rec.acquisition.MultiCastHardware initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.MultiCastHardwareHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.MultiCastHardwareHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.MultiCastHardwareHelper.type();
	}

}