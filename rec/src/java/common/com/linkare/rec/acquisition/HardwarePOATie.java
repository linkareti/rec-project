package com.linkare.rec.acquisition;

public class HardwarePOATie extends HardwarePOA
{

	// Constructors

	public HardwarePOATie( com.linkare.rec.acquisition.HardwareOperations delegate )
	{
		this._impl = delegate;
	}
	public HardwarePOATie( com.linkare.rec.acquisition.HardwareOperations delegate , org.omg.PortableServer.POA poa )
	{
		this._impl = delegate;
		this._poa      = poa;
	}
	public com.linkare.rec.acquisition.HardwareOperations _delegate()
	{
		return this._impl;
	}
	public void _delegate(com.linkare.rec.acquisition.HardwareOperations delegate )
	{
		this._impl = delegate;
	}
	public org.omg.PortableServer.POA _default_POA()
	{
		if(_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public com.linkare.rec.data.metadata.HardwareInfo getHardwareInfo()
	{
		return _impl.getHardwareInfo();
	} // getHardwareInfo

	public com.linkare.rec.acquisition.HardwareState getHardwareState()
	{
		return _impl.getHardwareState();
	} // getHardwareState

	public void registerDataClient(com.linkare.rec.acquisition.DataClient data_client) throws com.linkare.rec.acquisition.NotAuthorized
	{
		_impl.registerDataClient(data_client);
	} // registerDataClient

	public com.linkare.rec.acquisition.DataClient getDataClient()
	{
		return _impl.getDataClient();
	} // getDataClient

	public void configure(com.linkare.rec.data.config.HardwareAcquisitionConfig config) throws com.linkare.rec.acquisition.IncorrectStateException, com.linkare.rec.acquisition.WrongConfigurationException
	{
		_impl.configure(config);
	} // configure

	public com.linkare.rec.acquisition.DataProducer start(com.linkare.rec.acquisition.DataReceiver receiver) throws com.linkare.rec.acquisition.IncorrectStateException
	{
		return _impl.start(receiver);
	} // start

	public com.linkare.rec.acquisition.DataProducer startOutput(com.linkare.rec.acquisition.DataReceiver receiver, com.linkare.rec.acquisition.DataProducer data_source) throws com.linkare.rec.acquisition.IncorrectStateException
	{
		return _impl.startOutput(receiver, data_source);
	} // startOutput

	public void stop() throws com.linkare.rec.acquisition.IncorrectStateException
	{
		_impl.stop();
	} // stop

	public void reset() throws com.linkare.rec.acquisition.IncorrectStateException
	{
		_impl.reset();
	} // reset

	public com.linkare.rec.acquisition.DataProducer getDataProducer() throws com.linkare.rec.acquisition.IncorrectStateException, com.linkare.rec.acquisition.NotAvailableException
	{
		return _impl.getDataProducer();
	} // getDataProducer

	private com.linkare.rec.acquisition.HardwareOperations _impl;
	private org.omg.PortableServer.POA _poa;

} // class HardwarePOATie
