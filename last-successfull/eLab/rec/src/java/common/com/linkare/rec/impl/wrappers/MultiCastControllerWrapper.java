package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerOperations;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;

/**
 * This class works as a wrapper to the MCController Server It keeps checking
 * the connection state on an error A client may use the method isConnected if
 * in need to determine the current status of the connection to the server
 * 
 * @see com.linkare.rec.acquisition.MultiCastControllerOperations
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiCastControllerWrapper implements MultiCastControllerOperations {

	private static final Logger LOGGER = Logger.getLogger(MultiCastControllerWrapper.class.getName());

	/*
	 * The internal reference to the MultiCastController - the one that actually
	 * does the work
	 */
	private MultiCastController delegate = null;
	/* Cache the connection state */
	private boolean connected = false;

	/**
	 * Creates a new instance of MultiCastControllerWrapper
	 * 
	 * @param delegate The real MultiCastController that must do the work Note
	 *            that this will check the connection state on start, so
	 *            delegate should be a valid reference as obtained from CORBA
	 */
	public MultiCastControllerWrapper(final MultiCastController delegate) {
		// Save the delegate reference
		this.delegate = delegate;
		// Check that the delegate is up an' running
		checkConnect();
	}

	/*
	 * This method checks the connection with the MultiCastController Server
	 * through it's delegate client reference passed in in the constructor
	 */
	private void checkConnect() {
		if (delegate == null) {
			log(Level.WARNING, "MultiCastController has not been set! Please set it first...");
			connected = false;
		}
		try {
			// try to check if it the delegate is non_existent
			if (delegate._non_existent()) {
				connected = false;
			} else {
				connected = true;
			}
		} catch (final Exception e) {
			// Catch any possible exception in the remote call...
			logThrowable("Couldn't determine remote existence of MultiCastController. Assuming disconnected...", e);
			// assume it is not connected
			connected = false;
		}
	}

	/**
	 * This method verifies the connection to the server by invoking internal
	 * CORBA methods on the delegate reference
	 * 
	 * @return true if the connection can be determined from a non_existent
	 *         CORBA call, otherwise false
	 */
	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	/**
	 * This method determines if other wrapper is just another reference to the
	 * same server...
	 * 
	 * @param other the MultiCastControllerWrapper that needs to be checked
	 *            against this one
	 * @return true if this MultiCastControllerWrapper has a reference to the
	 *         same server as the other wrapper
	 * @see org.omg.CORBA.Object#_is_equivalent(org.omg.CORBA.Object)
	 */
	public boolean isSameDelegate(final MultiCastControllerWrapper other) {
		// The _is_equivalent
		return other.delegate._is_equivalent(delegate);
	}

	/**
	 * This method determines if other multicastcontroller reference is just
	 * another reference to the same server...
	 * 
	 * @param other the MultiCastController that needs to be checked against
	 *            this one's delegate
	 * @return true if this MultiCastControllerWrapper has a reference to the
	 *         same server as the other delegate
	 * @see org.omg.CORBA.Object#_is_equivalent(org.omg.CORBA.Object)
	 */
	public boolean isSameDelegate(final MultiCastController other) {
		return other._is_equivalent(delegate);
	}

	/**
	 * This method returns the internal delegate used to reference the server
	 * 
	 * @return The internal reference to the MultiCastController Server
	 */
	public MultiCastController getDelegate() {
		return delegate;
	}

	/**
	 * @see MultiCastControllerOperations#enumerateHardware(UserInfo)
	 * 
	 */
	@Override
	public MultiCastHardware[] enumerateHardware(final UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "MultiCastController has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.enumerateHardware(user);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}

		return null;
	}

	/**
	 * @see MultiCastControllerOperations#getClientList(com.linkare.rec.acquisition.UserInfo)
	 */
	@Override
	public UserInfo[] getClientList(final UserInfo user) throws NotRegistered, NotAuthorized {
		if (delegate == null) {
			log(Level.WARNING, "MultiCastController has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getClientList(user);
		} catch (final SystemException e) {
			logThrowable(null, e);
			checkConnect();
		}

		return null;
	}

	/**
	 * @see MultiCastControllerOperations#registerDataClient(DataClient)
	 */
	@Override
	public void registerDataClient(final DataClient data_client) throws MaximumClientsReached, NotAuthorized {
		if (delegate == null) {
			log(Level.WARNING, "MultiCastController has not been set! Please set it first...");
			return;
		}

		try {
			delegate.registerDataClient(data_client);
		} catch (final SystemException e) {
			logThrowable(null, e);
			checkConnect();
		}
	}

	/**
	 * @see MultiCastControllerOperations#registerHardware(Hardware)
	 */
	@Override
	public void registerHardware(final Hardware hardware) {
		if (delegate == null) {
			log(Level.WARNING, "MultiCastController has not been set! Please set it first...");
			return;
		}

		try {
			delegate.registerHardware(hardware);
		} catch (final SystemException e) {
			logThrowable(null, e);
			checkConnect();
		}
	}

	/**
	 * @see MultiCastControllerOperations#sendMessage(UserInfo, String, String)
	 * 
	 */
	@Override
	public void sendMessage(final UserInfo user, final String clientTo, final String message) throws NotRegistered,
			NotAuthorized {
		if (delegate == null) {
			log(Level.WARNING, "MultiCastController has not been set! Please set it first...");
			return;
		}

		try {
			delegate.sendMessage(user, clientTo, message);
		} catch (final SystemException e) {
			logThrowable(null, e);
			checkConnect();
		}
	}

	/*
	 * Internal method to log messages
	 */
	private void log(final Level l, final String message) {
		LOGGER.log(l, message);
	}

	/*
	 * Internal method to log exceptions
	 */
	private void logThrowable(final String message, final Throwable t) {
		LOGGER.log(Level.ALL, message, t);
	}
}
