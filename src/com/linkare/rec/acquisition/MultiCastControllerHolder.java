package com.linkare.rec.acquisition;

/**
* com/linkare/rec/acquisition/MultiCastControllerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Sabado, 17 de Janeiro de 2004 19H00m GMT
*/

public final class MultiCastControllerHolder implements org.omg.CORBA.portable.Streamable
{
  public com.linkare.rec.acquisition.MultiCastController value = null;

  public MultiCastControllerHolder ()
  {
  }

  public MultiCastControllerHolder (com.linkare.rec.acquisition.MultiCastController initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.linkare.rec.acquisition.MultiCastControllerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.linkare.rec.acquisition.MultiCastControllerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.linkare.rec.acquisition.MultiCastControllerHelper.type ();
  }

}
