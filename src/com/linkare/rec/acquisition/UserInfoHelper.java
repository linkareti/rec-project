package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/UserInfoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Terca-feira, 30 de Dezembro de 2003 11H33m GMT
*/


//Version 7.0 Addition
abstract public class UserInfoHelper
{
  private static String  _id = "IDL:com/linkare/rec/acquisition/UserInfo:1.0";

  public static void insert (org.omg.CORBA.Any a, com.linkare.rec.acquisition.UserInfo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.linkare.rec.acquisition.UserInfo extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (com.linkare.rec.acquisition.ClientNameHelper.id (), "ClientName", _tcOf_members0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "userName",
            _tcOf_members0,
            null);
          _tcOf_members0 = com.linkare.rec.acquisition.VTPropertyListHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "userProps",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.linkare.rec.acquisition.UserInfoHelper.id (), "UserInfo", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.linkare.rec.acquisition.UserInfo read (org.omg.CORBA.portable.InputStream istream)
  {
    com.linkare.rec.acquisition.UserInfo value = new com.linkare.rec.acquisition.UserInfo ();
    value.setUserName(istream.read_wstring ());
    value.setUserProps (com.linkare.rec.acquisition.VTPropertyListHelper.read (istream));
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.acquisition.UserInfo value)
  {
    ostream.write_wstring (value.getUserName());
    com.linkare.rec.acquisition.VTPropertyListHelper.write (ostream, value.getUserProps());
  }

}
