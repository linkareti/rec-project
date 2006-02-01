package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/NotOwnerException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Sexta-feira, 2 de Janeiro de 2004 15H12m GMT
*/

public final class NotOwnerException extends org.omg.CORBA.UserException
{
  public int errorCode = (int)0;

  //DataClient currentOwner;
  public String currentOwner = null;

  public NotOwnerException()
  {
    super(NotOwnerExceptionHelper.id());
  } // ctor

  public NotOwnerException(int _errorCode, String _currentOwner)
  {
    super(NotOwnerExceptionHelper.id());
    errorCode = _errorCode;
    currentOwner = _currentOwner;
  } // ctor


  public NotOwnerException(String $reason, int _errorCode, String _currentOwner)
  {
    super(NotOwnerExceptionHelper.id() + "  " + $reason);
    errorCode = _errorCode;
    currentOwner = _currentOwner;
  } // ctor

} // class NotOwnerException
