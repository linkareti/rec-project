package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.HardwareOperations;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareWrapper implements HardwareOperations {
	private Hardware delegate = null;
	private boolean connected = false;

	private static final Logger LOGGER = Logger.getLogger(HardwareWrapper.class.getName());

	/**
	 * Creates a new instance of HardwareWrapper
	 * 
	 * @param delegate
	 */
	public HardwareWrapper(final Hardware delegate) {
		this.delegate = delegate;
		checkConnect();
	}

	private void checkConnect() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			connected = false;
		}
		try {
			if (delegate._non_existent()) {
				connected = false;
			} else {
				connected = true;
			}
		} catch (final Exception e) {

			LOGGER.log(Level.SEVERE, "Couldn't determine remote existence of Hardware. Assuming disconnected...", e);
			connected = false;
		}
	}

	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config) throws IncorrectStateException,
			WrongConfigurationException {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.configure(config);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	@Override
	public DataClient getDataClient() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getDataClient();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public DataProducer getDataProducer() throws IncorrectStateException, NotAvailableException {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getDataProducer();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public HardwareInfo getHardwareInfo() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getHardwareInfo();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public HardwareState getHardwareState() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getHardwareState();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public void registerDataClient(final DataClient data_client) throws NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.registerDataClient(data_client);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	@Override
	public void reset() throws IncorrectStateException {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.reset();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	@Override
	public DataProducer start(final DataReceiver receiver) throws IncorrectStateException {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.start(receiver);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public DataProducer startOutput(final DataReceiver receiver, final DataProducer data_source)
			throws IncorrectStateException {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.startOutput(receiver, data_source);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public void stop() throws IncorrectStateException {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.stop();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	public boolean isSameDelegate(final HardwareWrapper other) {
		return other.delegate._is_equivalent(delegate);
	}

	public boolean isSameDelegate(final Hardware other) {
		return other._is_equivalent(delegate);
	}

	public Hardware getDelegate() {
		return delegate;
	}

}