package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/NotAvailableException.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

public final class NotAvailableException extends org.omg.CORBA.UserException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 626900809322540180L;
	public int errorCode = 0;

	public NotAvailableException() {
		super(NotAvailableExceptionHelper.id());
	} // ctor

	public NotAvailableException(final int _errorCode) {
		super(NotAvailableExceptionHelper.id());
		errorCode = _errorCode;
	} // ctor

	public NotAvailableException(final String $reason, final int _errorCode) {
		super(NotAvailableExceptionHelper.id() + "  " + $reason);
		errorCode = _errorCode;
	} // ctor

} // class NotAvailableException
