/*
 * DataProducerWrapper.java
 *
 * Created on 7 de Maio de 2003, 12:54
 */

package com.linkare.rec.impl.wrappers;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.omg.CORBA.SystemException;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerOperations;
import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DataProducerWrapper implements DataProducerOperations {

	private DataProducer delegate = null;
	private boolean connected = false;

	private static String DATA_PRODUCER_LOGGER = "DataProducer.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
		}
	}

	/** Creates a new instance of DataProducerWrapper 
	 * @param delegate */
	public DataProducerWrapper(final DataProducer delegate) {
		this.delegate = delegate;
		checkConnect();
	}

	public boolean isConnected() {
		checkConnect();
		return connected;
	}

	private void checkConnect() {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer  has not been set! Please set it first...");
			connected = false;
		}
		try {
			if (delegate._non_existent()) {
				connected = false;
			} else {
				connected = true;
			}
		} catch (final Exception e) {

			LoggerUtil.logThrowable("Couldn't determine remote existence of DataProducer. Assuming disconnected...", e,
					Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			connected = false;
		}
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionHeader() throws NotAvailableException {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getAcquisitionHeader();
		} catch (final SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			checkConnect();
			return null;
		}
	}

	@Override
	public SamplesPacket[] getSamples(final int num_packet_start, final int num_packet_end)
			throws NotAnAvailableSamplesPacketException {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getSamples(num_packet_start, num_packet_end);
		} catch (final SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			checkConnect();
			return null;
		}
	}

	@Override
	public String getDataProducerName() {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getDataProducerName();
		} catch (final SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			checkConnect();
			return null;
		}
	}

	@Override
	public int getMaxPacketNum() {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer has not been set! Please set it first...");
			return -1;
		}

		try {
			return delegate.getMaxPacketNum();
		} catch (final SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			checkConnect();
			return -1;
		}
	}

	@Override
	public DataProducerState getDataProducerState() {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer has not been set! Please set it first...");
			return null;
		}

		try {
			return delegate.getDataProducerState();
		} catch (final SystemException e) {
			LoggerUtil.logThrowable("Error calling getDataProducerState...", e,
					Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			checkConnect();
			return null;
		}
	}

	@Override
	public void registerDataReceiver(final DataReceiver data_receiver) throws MaximumClientsReached {
		if (delegate == null) {
			Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER).log(Level.WARNING,
					"DataProducer has not been set! Please set it first...");
			return;
		}

		try {
			delegate.registerDataReceiver(data_receiver);
		} catch (final SystemException e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(DataProducerWrapper.DATA_PRODUCER_LOGGER));
			checkConnect();
			return;
		}
	}

	public boolean isSameDelegate(final DataProducerWrapper other) {
		return other.delegate._is_equivalent(delegate);
	}

	public boolean isSameDelegate(final DataProducer other) {
		return other._is_equivalent(delegate);
	}

	public DataProducer getDelegate() {
		return delegate;
	}
}
