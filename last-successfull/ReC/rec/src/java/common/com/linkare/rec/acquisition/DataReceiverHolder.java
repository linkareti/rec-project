package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/DataReceiverHolder.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

public final class DataReceiverHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.DataReceiver value = null;

	public DataReceiverHolder() {
	}

	public DataReceiverHolder(com.linkare.rec.acquisition.DataReceiver initialValue) {
		value = initialValue;
	}

	public void _read(org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.DataReceiverHelper.read(i);
	}

	public void _write(org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.DataReceiverHelper.write(o, value);
	}

	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.DataReceiverHelper.type();
	}

}
