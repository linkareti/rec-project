package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/_DataClientStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Terca-feira, 30 de Dezembro de 2003 11H33m GMT
*/

public class _DataClientStub extends org.omg.CORBA.portable.ObjectImpl implements com.linkare.rec.acquisition.DataClient
{


  //Version 7.0 change: removed method getUser and getPassword and changed it to getUserInfo... wich is much more generic
  public com.linkare.rec.acquisition.UserInfo getUserInfo ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getUserInfo", true);
                $in = _invoke ($out);
                com.linkare.rec.acquisition.UserInfo $result = com.linkare.rec.acquisition.UserInfoHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getUserInfo (        );
            } finally {
                _releaseReply ($in);
            }
  } // getUserInfo


  //wstring getPassword();
  public void hardwareStateChange (com.linkare.rec.acquisition.HardwareState newState)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("hardwareStateChange", true);
                com.linkare.rec.acquisition.HardwareStateHelper.write ($out, newState);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                hardwareStateChange (newState        );
            } finally {
                _releaseReply ($in);
            }
  } // hardwareStateChange

  public void hardwareChange ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("hardwareChange", true);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                hardwareChange (        );
            } finally {
                _releaseReply ($in);
            }
  } // hardwareChange

  public void hardwareLockable (long millisecs_to_lock_success)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("hardwareLockable", true);
                $out.write_longlong (millisecs_to_lock_success);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                hardwareLockable (millisecs_to_lock_success        );
            } finally {
                _releaseReply ($in);
            }
  } // hardwareLockable


  //version 5 added suport for messages
  public void receiveMessage (String clientFrom, String clientTo, String message)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("receiveMessage", true);
                org.omg.CORBA.WStringValueHelper.write ($out, clientFrom);
                org.omg.CORBA.WStringValueHelper.write ($out, clientTo);
                $out.write_wstring (message);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                receiveMessage (clientFrom, clientTo, message        );
            } finally {
                _releaseReply ($in);
            }
  } // receiveMessage

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:com/linkare/rec/acquisition/DataClient:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _DataClientStub
