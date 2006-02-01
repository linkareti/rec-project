package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/DataClientPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Terca-feira, 30 de Dezembro de 2003 11H33m GMT
*/

public abstract class DataClientPOA extends org.omg.PortableServer.Servant implements com.linkare.rec.acquisition.DataClientOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getUserInfo", new java.lang.Integer (0));
    _methods.put ("hardwareStateChange", new java.lang.Integer (1));
    _methods.put ("hardwareChange", new java.lang.Integer (2));
    _methods.put ("hardwareLockable", new java.lang.Integer (3));
    _methods.put ("receiveMessage", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {

  //Version 7.0 change: removed method getUser and getPassword and changed it to getUserInfo... wich is much more generic
       case 0:  // com/linkare/rec/acquisition/DataClient/getUserInfo
       {
         com.linkare.rec.acquisition.UserInfo $result = null;
         $result = this.getUserInfo ();
         out = $rh.createReply();
         com.linkare.rec.acquisition.UserInfoHelper.write (out, $result);
         break;
       }


  //wstring getPassword();
       case 1:  // com/linkare/rec/acquisition/DataClient/hardwareStateChange
       {
         com.linkare.rec.acquisition.HardwareState newState = com.linkare.rec.acquisition.HardwareStateHelper.read (in);
         this.hardwareStateChange (newState);
         out = $rh.createReply();
         break;
       }

       case 2:  // com/linkare/rec/acquisition/DataClient/hardwareChange
       {
         this.hardwareChange ();
         out = $rh.createReply();
         break;
       }

       case 3:  // com/linkare/rec/acquisition/DataClient/hardwareLockable
       {
         long millisecs_to_lock_success = in.read_longlong ();
         this.hardwareLockable (millisecs_to_lock_success);
         out = $rh.createReply();
         break;
       }


  //version 5 added suport for messages
       case 4:  // com/linkare/rec/acquisition/DataClient/receiveMessage
       {
         String clientFrom = org.omg.CORBA.WStringValueHelper.read (in);
         String clientTo = org.omg.CORBA.WStringValueHelper.read (in);
         String message = in.read_wstring ();
         this.receiveMessage (clientFrom, clientTo, message);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:com/linkare/rec/acquisition/DataClient:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public DataClient _this() 
  {
    return DataClientHelper.narrow(
    super._this_object());
  }

  public DataClient _this(org.omg.CORBA.ORB orb) 
  {
    return DataClientHelper.narrow(
    super._this_object(orb));
  }


} // class DataClientPOA
