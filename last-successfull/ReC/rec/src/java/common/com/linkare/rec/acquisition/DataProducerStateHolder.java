package com.linkare.rec.acquisition;

/**
 * IdlCompile/com/linkare/rec/acquisition/DataProducerStateHolder.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Segunda-feira, 29 de Dezembro de 2003
 * 16H55m GMT
 */

// Version 7.0 Change - added state to DataProducer... simpler to maintain
public final class DataProducerStateHolder implements org.omg.CORBA.portable.Streamable {
	public com.linkare.rec.acquisition.DataProducerState value = null;

	public DataProducerStateHolder() {
	}

	public DataProducerStateHolder(final com.linkare.rec.acquisition.DataProducerState initialValue) {
		value = initialValue;
	}

	@Override
	public void _read(final org.omg.CORBA.portable.InputStream i) {
		value = com.linkare.rec.acquisition.DataProducerStateHelper.read(i);
	}

	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream o) {
		com.linkare.rec.acquisition.DataProducerStateHelper.write(o, value);
	}

	@Override
	public org.omg.CORBA.TypeCode _type() {
		return com.linkare.rec.acquisition.DataProducerStateHelper.type();
	}

}
