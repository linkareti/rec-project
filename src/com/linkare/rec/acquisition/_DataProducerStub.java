package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/_DataProducerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Sexta-feira, 2 de Janeiro de 2004 15H12m GMT
*/


//ok, not known at this time
public class _DataProducerStub extends org.omg.CORBA.portable.ObjectImpl implements com.linkare.rec.acquisition.DataProducer
{


  //::com::linkare::rec::data::metadata::HardwareAcquisitionHeader getAcquisitionHeader() raises (NotAvailableException);
  public com.linkare.rec.data.config.HardwareAcquisitionConfig getAcquisitionHeader () throws com.linkare.rec.acquisition.NotAvailableException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAcquisitionHeader", true);
                $in = _invoke ($out);
                com.linkare.rec.data.config.HardwareAcquisitionConfig $result = com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0"))
                    throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAcquisitionHeader (        );
            } finally {
                _releaseReply ($in);
            }
  } // getAcquisitionHeader


  //and getSamples(0,getMaxPacketNum());
  public com.linkare.rec.data.acquisition.SamplesPacket[] getSamples (int num_packet_start, int num_packet_end) throws com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getSamples", true);
                $out.write_long (num_packet_start);
                $out.write_long (num_packet_end);
                $in = _invoke ($out);
                com.linkare.rec.data.acquisition.SamplesPacket $result[] = com.linkare.rec.data.acquisition.SamplesPacketListHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:com/linkare/rec/acquisition/NotAnAvailableSamplesPacketException:1.0"))
                    throw com.linkare.rec.acquisition.NotAnAvailableSamplesPacketExceptionHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getSamples (num_packet_start, num_packet_end        );
            } finally {
                _releaseReply ($in);
            }
  } // getSamples


  //::org::omg::CORBA::WStringValue getConfiguratorURL();
  public String getDataProducerName ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getDataProducerName", true);
                $in = _invoke ($out);
                String $result = org.omg.CORBA.WStringValueHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getDataProducerName (        );
            } finally {
                _releaseReply ($in);
            }
  } // getDataProducerName


  //Version 7.0 Change this... now we may get any of the states...
  public com.linkare.rec.acquisition.DataProducerState getDataProducerState ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getDataProducerState", true);
                $in = _invoke ($out);
                com.linkare.rec.acquisition.DataProducerState $result = com.linkare.rec.acquisition.DataProducerStateHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getDataProducerState (        );
            } finally {
                _releaseReply ($in);
            }
  } // getDataProducerState

  public int getMaxPacketNum ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getMaxPacketNum", true);
                $in = _invoke ($out);
                int $result = $in.read_long ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getMaxPacketNum (        );
            } finally {
                _releaseReply ($in);
            }
  } // getMaxPacketNum

  public void registerDataReceiver (com.linkare.rec.acquisition.DataReceiver data_receiver) throws com.linkare.rec.acquisition.MaximumClientsReached
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("registerDataReceiver", true);
                com.linkare.rec.acquisition.DataReceiverHelper.write ($out, data_receiver);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                if (_id.equals ("IDL:com/linkare/rec/acquisition/MaximumClientsReached:1.0"))
                    throw com.linkare.rec.acquisition.MaximumClientsReachedHelper.read ($in);
                else
                    throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                registerDataReceiver (data_receiver        );
            } finally {
                _releaseReply ($in);
            }
  } // registerDataReceiver

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:com/linkare/rec/acquisition/DataProducer:1.0"};

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
} // class _DataProducerStub
