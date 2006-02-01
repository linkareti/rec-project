package com.linkare.rec.acquisition;


//Version 7.0 Addition
abstract public class PropertyHelper
{
  private static String  _id = "IDL:com/linkare/rec/acquisition/Property:1.0";

  public static void insert (org.omg.CORBA.Any a, com.linkare.rec.acquisition.Property that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.linkare.rec.acquisition.Property extract (org.omg.CORBA.Any a)
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
          _members0[0] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_any);
          _members0[1] = new org.omg.CORBA.StructMember (
            "value",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.linkare.rec.acquisition.PropertyHelper.id (), "Property", _members0);
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

  public static com.linkare.rec.acquisition.Property read (org.omg.CORBA.portable.InputStream istream)
  {
    com.linkare.rec.acquisition.Property value = new com.linkare.rec.acquisition.Property ();
    value.setName(istream.read_wstring ());
    value.setValue(istream.read_any ());
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.linkare.rec.acquisition.Property value)
  {
    ostream.write_wstring (value.getName());
    ostream.write_any (value.getValue());
  }

}
