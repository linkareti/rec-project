package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.DataReceiverOperations;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DataReceiverWrapper implements DataReceiverOperations {

	private DataReceiver delegate = null;
	private boolean connected = false;

	private static final Logger LOGGER = Logger.getLogger(DataReceiverWrapper.class.getName());

	/**
	 * Creates a new instance of DataReceiverWrapper
	 * 
	 * @param delegate
	 */
	public DataReceiverWrapper(final DataReceiver delegate) {
		this.delegate = delegate;
		checkConnect();
	}

	private void checkConnect() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataReceiver  has not been set! Please set it first...");
			connected = false;
		}
		try {
			if (delegate._non_existent()) {
				connected = false;
			} else {
				connected = true;
			}
		} catch (final Exception e) {

			LOGGER.log(Level.SEVERE, "Couldn't determine remote existence of DataReceiver. Assuming disconnected...", e);
			connected = false;
		}
	}

	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	public boolean isSameDelegate(final DataReceiverWrapper other) {
		return other.delegate._is_equivalent(delegate);
	}

	public boolean isSameDelegate(final DataReceiver other) {
		return other._is_equivalent(delegate);
	}

	public DataReceiver getDelegate() {
		return delegate;
	}

	@Override
	public void newSamples(final int largestNumPacket) {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataReceiver  has not been set! Please set it first...");
			return;
		}
		try {
			delegate.newSamples(largestNumPacket);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

	@Override
	public void stateChanged(final DataProducerState newState) {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataReceiver  has not been set! Please set it first...");
			return;
		}
		try {
			delegate.stateChanged(newState);
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

	@Override
	public void clientsListChanged() {
		if (delegate == null) {
			LOGGER.log(Level.WARNING, "DataReceiver  has not been set! Please set it first...");
			return;
		}
		try {
			delegate.clientsListChanged();
		} catch (final SystemException e) {
			LOGGER.log(Level.SEVERE, null, e);
			checkConnect();
		}
	}

}
