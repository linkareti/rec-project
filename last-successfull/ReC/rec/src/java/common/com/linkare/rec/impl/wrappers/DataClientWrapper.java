package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.UserInfo;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DataClientWrapper implements DataClientOperations {
	private DataClient delegate = null;
	private boolean connected = false;

	private static final Logger LOGGER = Logger.getLogger(DataClientWrapper.class.getName());

	/**
	 * Creates a new instance of DataClientWrapper
	 * 
	 * @param delegate
	 */
	public DataClientWrapper(final DataClient delegate) {
		this.delegate = delegate;
		checkConnect();
	}

	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	private void checkConnect() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataClient  has not been set! Please set it first...");
			connected = false;
			return;
		}
		try {

			if (delegate._non_existent()) {
				connected = false;
			} else {
				connected = true;
			}
		} catch (final Exception e) {

			LOGGER.log(Level.SEVERE, "Couldn't determine remote existence of DataClient. Assuming disconnected...", e);
			connected = false;
		}
	}

	@Override
	public void hardwareChange() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataClient  has not been set! Please set it first...");
			return;
		}

		try {
			delegate.hardwareChange();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	@Override
	public void hardwareLockable(final long millisecs_to_lock_success) {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataClient  has not been set! Please set it first...");
			return;
		}

		try {
			delegate.hardwareLockable(millisecs_to_lock_success);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

	}

	@Override
	public void hardwareStateChange(final HardwareState newState) {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataClient  has not been set! Please set it first...");
			return;
		}

		try {
			delegate.hardwareStateChange(newState);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, "Couldn't call hardwareStateChange on DataClient...", e);
			checkConnect();
		}
	}

	@Override
	public void receiveMessage(final String clientFrom, final String clientTo, final String message) {

		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataClient  has not been set! Please set it first...");
			return;
		}

		try {
			delegate.receiveMessage(clientFrom, clientTo, message);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

	public boolean isSameDelegate(final DataClientWrapper other) {
		return other.delegate._is_equivalent(delegate);
	}

	public boolean isSameDelegate(final DataClient other) {
		return other._is_equivalent(delegate);
	}

	public DataClient getDelegate() {
		return delegate;
	}

	@Override
	public UserInfo getUserInfo() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataClient  has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getUserInfo();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

}
