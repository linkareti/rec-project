package com.linkare.rec.acquisition;

public class HardwarePOATie extends HardwarePOA {

	// Constructors

	public HardwarePOATie(final com.linkare.rec.acquisition.HardwareOperations delegate) {
		_impl = delegate;
	}

	public HardwarePOATie(final com.linkare.rec.acquisition.HardwareOperations delegate,
			final org.omg.PortableServer.POA poa) {
		_impl = delegate;
		_poa = poa;
	}

	public com.linkare.rec.acquisition.HardwareOperations _delegate() {
		return _impl;
	}

	public void _delegate(final com.linkare.rec.acquisition.HardwareOperations delegate) {
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

	@Override
	public com.linkare.rec.data.metadata.HardwareInfo getHardwareInfo() {
		return _impl.getHardwareInfo();
	} // getHardwareInfo

	@Override
	public com.linkare.rec.acquisition.HardwareState getHardwareState() {
		return _impl.getHardwareState();
	} // getHardwareState

	@Override
	public void registerDataClient(final com.linkare.rec.acquisition.DataClient data_client)
			throws com.linkare.rec.acquisition.NotAuthorized {
		_impl.registerDataClient(data_client);
	} // registerDataClient

	@Override
	public com.linkare.rec.acquisition.DataClient getDataClient() {
		return _impl.getDataClient();
	} // getDataClient

	@Override
	public void configure(final com.linkare.rec.data.config.HardwareAcquisitionConfig config)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.WrongConfigurationException {
		_impl.configure(config);
	} // configure

	@Override
	public com.linkare.rec.acquisition.DataProducer start(final com.linkare.rec.acquisition.DataReceiver receiver)
			throws com.linkare.rec.acquisition.IncorrectStateException {
		return _impl.start(receiver);
	} // start

	@Override
	public com.linkare.rec.acquisition.DataProducer startOutput(
			final com.linkare.rec.acquisition.DataReceiver receiver,
			final com.linkare.rec.acquisition.DataProducer data_source)
			throws com.linkare.rec.acquisition.IncorrectStateException {
		return _impl.startOutput(receiver, data_source);
	} // startOutput

	@Override
	public void stop() throws com.linkare.rec.acquisition.IncorrectStateException {
		_impl.stop();
	} // stop

	@Override
	public void reset() throws com.linkare.rec.acquisition.IncorrectStateException {
		_impl.reset();
	} // reset

	@Override
	public com.linkare.rec.acquisition.DataProducer getDataProducer()
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException {
		return _impl.getDataProducer();
	} // getDataProducer

	private com.linkare.rec.acquisition.HardwareOperations _impl;
	private org.omg.PortableServer.POA _poa;

} // class HardwarePOATie
