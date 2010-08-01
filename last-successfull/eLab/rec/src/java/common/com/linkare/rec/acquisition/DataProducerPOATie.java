package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/DataProducerPOATie.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

// ok, not known at this time
public class DataProducerPOATie extends DataProducerPOA {

	// Constructors

	public DataProducerPOATie(com.linkare.rec.acquisition.DataProducerOperations delegate) {
		this._impl = delegate;
	}

	public DataProducerPOATie(com.linkare.rec.acquisition.DataProducerOperations delegate,
			org.omg.PortableServer.POA poa) {
		this._impl = delegate;
		this._poa = poa;
	}

	public com.linkare.rec.acquisition.DataProducerOperations _delegate() {
		return this._impl;
	}

	public void _delegate(com.linkare.rec.acquisition.DataProducerOperations delegate) {
		this._impl = delegate;
	}

	public org.omg.PortableServer.POA _default_POA() {
		if (_poa != null) {
			return _poa;
		} else {
			return super._default_POA();
		}
	}

	// ::com::linkare::rec::data::metadata::HardwareAcquisitionHeader
	// getAcquisitionHeader() raises (NotAvailableException);
	public com.linkare.rec.data.config.HardwareAcquisitionConfig getAcquisitionHeader()
			throws com.linkare.rec.acquisition.NotAvailableException {
		return _impl.getAcquisitionHeader();
	} // getAcquisitionHeader

	// and getSamples(0,getMaxPacketNum());
	public com.linkare.rec.data.acquisition.SamplesPacket[] getSamples(int num_packet_start, int num_packet_end)
			throws com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException {
		return _impl.getSamples(num_packet_start, num_packet_end);
	} // getSamples

	// ::org::omg::CORBA::WStringValue getConfiguratorURL();
	public String getDataProducerName() {
		return _impl.getDataProducerName();
	} // getDataProducerName

	// Version 7.0 Change this... now we may get any of the states...
	public com.linkare.rec.acquisition.DataProducerState getDataProducerState() {
		return _impl.getDataProducerState();
	} // getDataProducerState

	public int getMaxPacketNum() {
		return _impl.getMaxPacketNum();
	} // getMaxPacketNum

	public void registerDataReceiver(com.linkare.rec.acquisition.DataReceiver data_receiver)
			throws com.linkare.rec.acquisition.MaximumClientsReached {
		_impl.registerDataReceiver(data_receiver);
	} // registerDataReceiver

	private com.linkare.rec.acquisition.DataProducerOperations _impl;
	private org.omg.PortableServer.POA _poa;

} // class DataProducerPOATie
