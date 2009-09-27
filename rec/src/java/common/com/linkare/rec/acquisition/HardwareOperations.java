package com.linkare.rec.acquisition;

public interface HardwareOperations {
	com.linkare.rec.data.metadata.HardwareInfo getHardwareInfo();

	com.linkare.rec.acquisition.HardwareState getHardwareState();

	void registerDataClient(com.linkare.rec.acquisition.DataClient data_client)
			throws com.linkare.rec.acquisition.NotAuthorized;

	com.linkare.rec.acquisition.DataClient getDataClient();

	void configure(com.linkare.rec.data.config.HardwareAcquisitionConfig config)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.WrongConfigurationException;

	com.linkare.rec.acquisition.DataProducer start(com.linkare.rec.acquisition.DataReceiver receiver)
			throws com.linkare.rec.acquisition.IncorrectStateException;

	com.linkare.rec.acquisition.DataProducer startOutput(com.linkare.rec.acquisition.DataReceiver receiver,
			com.linkare.rec.acquisition.DataProducer data_source)
			throws com.linkare.rec.acquisition.IncorrectStateException;

	void stop() throws com.linkare.rec.acquisition.IncorrectStateException;

	void reset() throws com.linkare.rec.acquisition.IncorrectStateException;

	com.linkare.rec.acquisition.DataProducer getDataProducer()
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException;
} // interface HardwareOperations
