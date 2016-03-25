package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/DataReceiverPOATie.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

public class DataReceiverPOATie extends DataReceiverPOA {

	// Constructors

	public DataReceiverPOATie(final com.linkare.rec.acquisition.DataReceiverOperations delegate) {
		_impl = delegate;
	}

	public DataReceiverPOATie(final com.linkare.rec.acquisition.DataReceiverOperations delegate,
			final org.omg.PortableServer.POA poa) {
		_impl = delegate;
		_poa = poa;
	}

	public com.linkare.rec.acquisition.DataReceiverOperations _delegate() {
		return _impl;
	}

	public void _delegate(final com.linkare.rec.acquisition.DataReceiverOperations delegate) {
		_impl = delegate;
	}

	@Override
	public org.omg.PortableServer.POA _default_POA() {
		if (_poa != null) {
			return _poa;
		} else {
			return super._default_POA();
		}
	}

	// Version 7.0 Changed this... we must only inform of the largest packet
	// known... all the packets before should be there
	@Override
	public void newSamples(final int largestNumPacket) {
		_impl.newSamples(largestNumPacket);
	} // newSamples

	// Version 7.0 Changed this... now we may inform of changes in state
	@Override
	public void stateChanged(final com.linkare.rec.acquisition.DataProducerState newState) {
		_impl.stateChanged(newState);
	} // stateChanged

	// Version 7.0 Changed this... now we may inform of changes in clientsList
	@Override
	public void clientsListChanged() {
		_impl.clientsListChanged();
	} // clientsListChanged

	private com.linkare.rec.acquisition.DataReceiverOperations _impl;
	private org.omg.PortableServer.POA _poa;

} // class DataReceiverPOATie