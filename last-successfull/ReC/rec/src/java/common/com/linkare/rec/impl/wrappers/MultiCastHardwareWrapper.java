/*
 * MultiCastHardwareWrapper.java
 *
 * Created on 2 de Abril de 2003, 17:45
 */

package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.LogManager;
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
import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiCastHardwareWrapper implements MultiCastHardwareOperations {
	private MultiCastHardware delegate = null;
	private boolean connected = false;
	/** Creates a new instance of MultiCastHardwareWrapper */
	private static String MC_HARDWARE_LOGGER = "MultiCastHardware.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(MC_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MC_HARDWARE_LOGGER));
		}
	}

	public MultiCastHardwareWrapper(MultiCastHardware delegate) {
		this.delegate = delegate;
		checkConnect();
	}

	private void checkConnect() {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			connected = false;
		}
		try {
			if (delegate._non_existent())
				connected = false;
			else
				connected = true;
		} catch (Exception e) {

			LoggerUtil.logThrowable(
					"Couldn't determine remote existence of MultiCastHardware. Assuming disconnected...", e, Logger
							.getLogger(MC_HARDWARE_LOGGER));
			connected = false;
		}
	}

	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	public boolean isSameDelegate(MultiCastHardwareWrapper other) {
		return other.delegate._is_equivalent(this.delegate);
	}

	public boolean isSameDelegate(MultiCastHardware other) {
		return other._is_equivalent(this.delegate);
	}

	public MultiCastHardware getDelegate() {
		return delegate;
	}

	public void configure(UserInfo user, HardwareAcquisitionConfig configuration) throws IncorrectStateException,
			NotAvailableException, WrongConfigurationException, NotOwnerException, NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.configure(user, configuration);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}
	}

	public UserInfo[] getClientList(UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getClientList(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

		return null;
	}

	public DataProducer getDataProducer(UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getDataProducer(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

		return null;
	}

	public HardwareInfo getHardwareInfo(UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getHardwareInfo(user);
		} catch (SystemException e) {
			e.printStackTrace();
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

		return null;
	}

	public HardwareState getHardwareState(UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getHardwareState(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

		return null;
	}

	public void registerDataClient(DataClient data_client) throws NotAvailableException, MaximumClientsReached,
			NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.registerDataClient(data_client);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}
	}

	public void requireLock(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.requireLock(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}
	}

	public void reset(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.reset(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

	}

	public void sendMessage(UserInfo userFrom, String clientTo, String message) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.sendMessage(userFrom, clientTo, message);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}
	}

	public DataProducer start(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.start(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

		return null;
	}

	public DataProducer startOutput(UserInfo user, DataProducer data_source) throws IncorrectStateException,
			NotAvailableException, NotOwnerException, NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.startOutput(user, data_source);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

		return null;
	}

	public void stop(UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotRegistered, NotAuthorized {
		if (delegate == null) {
			Logger.getLogger(MC_HARDWARE_LOGGER)
					.log(Level.WARNING, "Hardware has not been set! Please set it first...");
			return;
		}

		try {
			delegate.stop(user);
		} catch (SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(MC_HARDWARE_LOGGER));
			checkConnect();
		}

	}

}
