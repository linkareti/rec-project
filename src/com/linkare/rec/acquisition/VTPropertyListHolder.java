package com.linkare.rec.acquisition;

/**
* com/linkare/rec/acquisition/VTPropertyListHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Terca-feira, 30 de Dezembro de 2003 11H33m GMT
*/

public final class VTPropertyListHolder implements org.omg.CORBA.portable.Streamable
{
  public com.linkare.rec.acquisition.Property[] value;

  public VTPropertyListHolder ()
  {
  }

  public VTPropertyListHolder (com.linkare.rec.acquisition.Property[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.linkare.rec.acquisition.VTPropertyListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.linkare.rec.acquisition.VTPropertyListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.linkare.rec.acquisition.VTPropertyListHelper.type ();
  }

}
