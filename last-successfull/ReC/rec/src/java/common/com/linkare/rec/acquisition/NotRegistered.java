package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/NotRegistered.java . Generated by the IDL-to-Java
 * compiler (portable), version "3.1" from I:/Projects/REC/IdlCompile/ReC7.idl
 * Sexta-feira, 2 de Janeiro de 2004 15H12m GMT
 */

public final class NotRegistered extends org.omg.CORBA.UserException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4973483160449363596L;
	public int errorCode = 0;

	public NotRegistered() {
		super(NotRegisteredHelper.id());
	} // ctor

	public NotRegistered(final int _errorCode) {
		super(NotRegisteredHelper.id());
		errorCode = _errorCode;
	} // ctor

	public NotRegistered(final String $reason, final int _errorCode) {
		super(NotRegisteredHelper.id() + "  " + $reason);
		errorCode = _errorCode;
	} // ctor

} // class NotRegistered
