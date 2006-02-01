package com.linkare.rec.acquisition;


/**
* com/linkare/rec/acquisition/DataProducerOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from I:/Projects/REC/IdlCompile/ReC7.idl
* Sexta-feira, 2 de Janeiro de 2004 15H12m GMT
*/


//ok, not known at this time
public interface DataProducerOperations 
{

  //::com::linkare::rec::data::metadata::HardwareAcquisitionHeader getAcquisitionHeader() raises (NotAvailableException);
  com.linkare.rec.data.config.HardwareAcquisitionConfig getAcquisitionHeader () throws com.linkare.rec.acquisition.NotAvailableException;

  //and getSamples(0,getMaxPacketNum());
  com.linkare.rec.data.acquisition.SamplesPacket[] getSamples (int num_packet_start, int num_packet_end) throws com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;

  //::org::omg::CORBA::WStringValue getConfiguratorURL();
  String getDataProducerName ();

  //Version 7.0 Change this... now we may get any of the states...
  com.linkare.rec.acquisition.DataProducerState getDataProducerState ();
  int getMaxPacketNum ();
  void registerDataReceiver (com.linkare.rec.acquisition.DataReceiver data_receiver) throws com.linkare.rec.acquisition.MaximumClientsReached;
} // interface DataProducerOperations
