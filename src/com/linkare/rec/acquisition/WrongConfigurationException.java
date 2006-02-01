package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/WrongConfigurationException.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Sexta-feira, 2 de Janeiro de 2004 15H12m GMT
*/

public final class WrongConfigurationException extends org.omg.CORBA.UserException
{
  public int errorCode = (int)0;

  public WrongConfigurationException()
  {
    super(WrongConfigurationExceptionHelper.id());
  } // ctor

  public WrongConfigurationException(int _errorCode)
  {
    super(WrongConfigurationExceptionHelper.id());
    errorCode = _errorCode;
  } // ctor


  public WrongConfigurationException(String $reason, int _errorCode)
  {
    super(WrongConfigurationExceptionHelper.id() + "  " + $reason);
    errorCode = _errorCode;
  } // ctor

} // class WrongConfigurationException
