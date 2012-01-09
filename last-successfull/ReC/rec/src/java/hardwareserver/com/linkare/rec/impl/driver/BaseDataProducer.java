package com.linkare.rec.impl.driver;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.omg.PortableServer.Servant;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerHelper;
import com.linkare.rec.acquisition.DataProducerOperations;
import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.data.SamplesPacketSourceEvent;
import com.linkare.rec.impl.data.SamplesPacketSourceEventListener;
import com.linkare.rec.impl.data.SamplesSourceEvent;
import com.linkare.rec.impl.data.SamplesSourcePacketizer;
import com.linkare.rec.impl.events.DataProducerStateChangeEvent;
import com.linkare.rec.impl.exceptions.NotAnAvailableSamplesPacketExceptionConstants;
import com.linkare.rec.impl.exceptions.NotAvailableExceptionConstants;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.QueueLogger;
import com.linkare.rec.impl.wrappers.DataReceiverWrapper;

public class BaseDataProducer implements DataProducerOperations, QueueLogger {
	private DataProducerState dataProducerState = DataProducerState.DP_WAITING;
	private static final Logger LOGGER = Logger.getLogger(BaseDataProducer.class.getName());

	// private transient BaseHardware hardware=null;
	private transient IDataSource dataSource = null;
	private transient boolean dataSourceInitiated = false;
	private transient DataReceiverWrapper dataReceiver = null;
	private transient DataProducer _this = null;
	private SamplesSourcePacketizer packetizer = null;
	private EventQueue eventQueueDataReceiver = null;

	// private helper methods
	public DataProducer _this() {
		if (_this != null) {
			return _this;
		}

		try {
			Servant registeredServant = ORBBean.getORBBean().registerAutoIdRootPOAServant(DataProducer.class, this,
					null);
			org.omg.CORBA.Object thisReference = ORBBean.getORBBean().getAutoIdRootPOA()
					.servant_to_reference(registeredServant);
			_this = DataProducerHelper.narrow(thisReference);
			return _this;
		} catch (final Exception e) {
			LOGGER.log(Level.WARNING, "Couldn't Register this BaseDataProducer with ORB", e);
			return null;
		}

	}

	public void stopNow() {
		if (dataSource != null) {
			dataSource.stopNow();
		}

		// informProducerIsStopedAsynch();
	}

	/**
	 * Creates a new instance of SimulationDataProducerImpl
	 * 
	 * @param dataReceiver
	 */
	public BaseDataProducer(final DataReceiver dataReceiver) {

		eventQueueDataReceiver = new EventQueue(new DataProducerEventsDispatcher(), this.getClass().getSimpleName(),
				this);
		try {
			registerDataReceiver(dataReceiver);
		} catch (final MaximumClientsReached e) {
			LOGGER.log(Level.WARNING, "Unable to register DataReceiver with this BaseDataProducer", e);
		}
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionHeader() throws NotAvailableException {
		if (dataSource != null) {
			return dataSource.getAcquisitionHeader();
		}

		throw new NotAvailableException(NotAvailableExceptionConstants.NO_ACQ_HEADER);
	}

	private class DataProducerEventsDispatcher implements EventQueueDispatcher {

		/**
		 * 
		 */

		@Override
		public void dispatchEvent(final Object o) {
			if (o instanceof SamplesPacketSourceEvent) {
				final int num_packet = ((SamplesPacketSourceEvent) o).getPacketLargestIndex();
				if (dataReceiver != null) {
					try {
						LOGGER.log(Level.INFO, "New data packet available : " + num_packet);
						dataReceiver.newSamples(num_packet);
					} catch (final Exception e) {
						LOGGER.log(Level.WARNING, "Exception informing DataReceiver of new samples " + num_packet, e);
					}
				}
			} else if (o instanceof DataProducerStateChangeEvent) {
				if (dataReceiver != null) {
					final DataProducerStateChangeEvent event = (DataProducerStateChangeEvent) o;
					try {
						LOGGER.log(Level.INFO, "Data Producer is:" + event.getDataProducerState());
						dataReceiver.stateChanged(event.getDataProducerState());
					} catch (final Exception e) {
						LOGGER.log(Level.WARNING, "Exception informing DataReceiver of data Producer stoped!", e);
					}
				}
			}
		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY + 1;
		}

	}

	/** Utility field used by event firing mechanism. */
	private EventListenerList listenerList = null;

	private void fireNewSamples(final SamplesPacketSourceEvent evt) {
		LOGGER.fine("New packet available : " + evt.getPacketLargestIndex());
		setDataProducerState(DataProducerState.DP_STARTED);
		eventQueueDataReceiver.addEvent(evt);
	}

	private void fireStateChanged() {
		LOGGER.fine("Producer is now :" + getDataProducerState());
		eventQueueDataReceiver.addEvent(new DataProducerStateChangeEvent(getDataProducerState()));
	}

	@Override
	public SamplesPacket[] getSamples(final int packetStartIndex, final int packetEndIndex)
			throws NotAnAvailableSamplesPacketException {
		try {
			final SamplesPacket[] packets = packetizer.getSamplesPackets(packetStartIndex, packetEndIndex);
			if (packetEndIndex == getMaxPacketNum()) {
				fireDataProducerIsEmpty(this);
			}
			return packets;
		} catch (final SamplesPacketReadException e) {
			throw new NotAnAvailableSamplesPacketException(
					NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, e.getErrorPacketNumber());
		}
	}

	@Override
	public String getDataProducerName() {
		return dataSource.getName();
	}

	@Override
	public int getMaxPacketNum() {
		return packetizer.size() - 1;
	}

	@Override
	public void registerDataReceiver(final DataReceiver dataReceiver) throws MaximumClientsReached {
		this.dataReceiver = new DataReceiverWrapper(dataReceiver);
	}

	/**
	 * Registers BaseDataProducerListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addBaseDataProducerListener(final BaseDataProducerListener listener) {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		listenerList.add(BaseDataProducerListener.class, listener);
	}

	/**
	 * Removes BaseDataProducerListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeBaseDataProducerListener(final BaseDataProducerListener listener) {
		listenerList.remove(BaseDataProducerListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireDataProducerIsEmpty(final BaseDataProducer event) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == BaseDataProducerListener.class) {
				((BaseDataProducerListener) listeners[i + 1]).baseDataProducerIsEmpty(event);
			}
		}
	}

	/**
	 * Getter for property dataProducerState.
	 * 
	 * @return Value of property dataProducerState.
	 * 
	 */
	@Override
	public DataProducerState getDataProducerState() {
		return dataProducerState;
	}

	/**
	 * Setter for property dataProducerState.
	 * 
	 * @param dataProducerState New value of property dataProducerState.
	 * 
	 */
	public void setDataProducerState(final DataProducerState dataProducerState) {
		if (dataProducerState.equals(this.dataProducerState)) {
			return;
		}
		this.dataProducerState = dataProducerState;
		fireStateChanged();
	}

	public void dataSourceStateWaiting() {
		setDataProducerState(DataProducerState.DP_WAITING);
	}

	public void dataSourceStateStarted() {
		if (packetizer.size() > 0) {
			setDataProducerState(DataProducerState.DP_STARTED);
		} else {
			setDataProducerState(DataProducerState.DP_STARTED_NODATA);
		}
	}

	public void dataSourceStateEnded() {
		setDataProducerState(DataProducerState.DP_ENDED);
	}

	public void dataSourceStateStoped() {
		setDataProducerState(DataProducerState.DP_STOPED);
	}

	public void dataSourceStateError() {
		setDataProducerState(DataProducerState.DP_ERROR);
	}

	/**
	 * @return the dataSource
	 */
	public IDataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(final IDataSource dataSource) {
		if (!dataSourceInitiated) {
			dataSourceInitiated = true;
			this.dataSource = dataSource;

			packetizer = new SamplesSourcePacketizer(dataSource.getAcquisitionHeader().getSelectedFrequency(),
					dataSource.getPacketSize(), (int) Math.ceil((double) dataSource.getTotalSamples()
							/ (double) dataSource.getPacketSize()), dataSource.getTotalSamples());

			packetizer.addSamplesPacketSourceEventListener(new SamplesPacketSourceEventListener() {
				@Override
				public void newSamplesPackets(final SamplesPacketSourceEvent evt) {
					fireNewSamples(evt);
				}
			});
			packetizer.setSamplesSource(dataSource);

			dataSource.addIDataSourceListener(new IDataSourceListener() {
				@Override
				public void dataSourceWaiting() {
					dataSourceStateWaiting();
				}

				@Override
				public void dataSourceStarted() {
					dataSourceStateStarted();
				}

				@Override
				public void dataSourceEnded() {
					dataSourceStateEnded();
				}

				@Override
				public void dataSourceStoped() {
					dataSourceStateStoped();
				}

				@Override
				public void dataSourceError() {
					dataSourceStateError();
				}

				@Override
				public void newSamples(final SamplesSourceEvent evt) {
				}
			});
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void log(final Level debugLevel, final String message) {
		LOGGER.log(debugLevel, message);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logThrowable(final String message, final Throwable t) {
		LOGGER.log(Level.SEVERE, message, t);
	}

}