package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/NotAuthorizedHolder.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

public final class NotAuthorizedHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.NotAuthorized value = null;

	public NotAuthorizedHolder() {
	}

	public NotAuthorizedHolder(final com.linkare.rec.acquisition.NotAuthorized initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.NotAuthorizedHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.NotAuthorizedHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.NotAuthorizedHelper.type();
	}

}
