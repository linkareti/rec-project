package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.MultiCastHardwareOperations;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.NotOwnerException;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiCastHardwareWrapper implements MultiCastHardwareOperations {
	private MultiCastHardware delegate = null;
	private boolean connected = false;

	private static final Logger LOGGER = Logger.getLogger(MultiCastHardwareWrapper.class.getName());

	/**
	 * Creates a new instance of MultiCastHardwareWrapper
	 * 
	 * @param delegate
	 */
	public MultiCastHardwareWrapper(final MultiCastHardware delegate) {
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

			LOGGER.log(Level.SEVERE,
					"Couldn't determine remote existence of MultiCastHardware. Assuming disconnected...", e);
			connected = false;
		}
	}

	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	public boolean isSameDelegate(final MultiCastHardwareWrapper other) {
		return other.delegate._is_equivalent(delegate);
	}

	public boolean isSameDelegate(final MultiCastHardware other) {
		return other._is_equivalent(delegate);
	}

	public MultiCastHardware getDelegate() {
		return delegate;
	}

	@Override
	public void configure(final UserInfo user, final HardwareAcquisitionConfig configuration)
			throws IncorrectStateException, NotAvailableException, WrongConfigurationException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.configure(user, configuration);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

	@Override
	public UserInfo[] getClientList(final UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getClientList(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public DataProducer getDataProducer(final UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getDataProducer(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public HardwareInfo getHardwareInfo(final UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getHardwareInfo(user);
		} catch (final SystemException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public HardwareState getHardwareState(final UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getHardwareState(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public void registerDataClient(final DataClient data_client) throws NotAvailableException, MaximumClientsReached,
			NotAuthorized {
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
	public void requireLock(final UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotOwnerException, NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.requireLock(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

	@Override
	public void reset(final UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.reset(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	@Override
	public void sendMessage(final UserInfo userFrom, final String clientTo, final String message) throws NotRegistered,
			NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.sendMessage(userFrom, clientTo, message);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

	@Override
	public DataProducer start(final UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotOwnerException, NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.start(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public DataProducer startOutput(final UserInfo user, final DataProducer data_source)
			throws IncorrectStateException, NotAvailableException, NotOwnerException, NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.startOutput(user, data_source);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	@Override
	public void stop(final UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.stop(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

}
