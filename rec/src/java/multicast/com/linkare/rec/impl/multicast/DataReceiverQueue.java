package com.linkare.rec.impl.multicast;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.impl.events.DataProducerStateChangeEvent;
import com.linkare.rec.impl.events.NewPoisonSamplesEvent;
import com.linkare.rec.impl.events.NewSamplesEvent;
import com.linkare.rec.impl.exceptions.MaximumClientsReachedConstants;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */

public class DataReceiverQueue implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1810449051214754798L;

	private boolean shutDown = false;

	private int maximumDataReceivers = 1;

	private IDataReceiverQueueListener dataReceiverQueueListener = null;

	// private internal state variables
	private final List<DataReceiverForQueue> queueOrg = new LinkedList<DataReceiverForQueue>();

	private final EventQueue messageQueue = new EventQueue(new DataReceiverQueueDispatcher(), this.getClass()
			.getSimpleName());

	private final DataReceiversConnectionCheck dataReceiversConnectionChecker = new DataReceiversConnectionCheck();

	private final IDataReceiverForQueueListener dataReceiverForQueueAdapter = new DataReceiverForQueueAdapter();

	private static final Logger LOGGER = Logger.getLogger(DataReceiverQueue.class.getName());

	/**
	 * Creates a new instance of HardwareDataReceiverQueue
	 * 
	 * @param dataReceiverQueueListener
	 * @param maximumDataReceivers
	 */
	public DataReceiverQueue(final IDataReceiverQueueListener dataReceiverQueueListener, final int maximumDataReceivers) {
		setDataReceiverQueueListener(dataReceiverQueueListener);
		setMaximumDataReceivers(maximumDataReceivers);
		// dataReceiversConnectionChecker.start();
	}

	/* Delegate Implementations for MCDataProducer */
	// message queue dispatchers
	public void stateChanged(final DataProducerState newState) {
		messageQueue.addEvent(new DataProducerStateChangeEvent(newState));
	}

	public void newSamples(final int largestPacketNum) {
		messageQueue.addEvent(new NewSamplesEvent(largestPacketNum));
	}

	public void newPoisonSamples(final int largestPacketNum) {
		messageQueue.addEvent(new NewPoisonSamplesEvent(largestPacketNum));
	}

	// Helper function for chat messages...

	public void shutdown() {
		if (shutDown) {
			return;
		}
		shutDown = true;
		LOGGER.log(Level.INFO, "DataReceiverQueue - shut down process initiated!");
		LOGGER.log(Level.INFO, "DataReceiverQueue - shutting down message queue!");
		messageQueue.shutdown();
		LOGGER.log(Level.INFO, "DataReceiverQueue - message queue is shut down!");

		LOGGER.log(Level.INFO, "DataReceiverQueue - shutting down dataReceivers connection checker!");
		dataReceiversConnectionChecker.shutdown();
		LOGGER.log(Level.INFO, "DataReceiverQueue - dataReceivers connection checker is shut down!");

		LOGGER.log(Level.INFO, "DataReceiverQueue - shutting down dataReceivers!");
		final Iterator<DataReceiverForQueue> iter = iterator();
		while (iter.hasNext()) {
			final DataReceiverForQueue drfq = iter.next();
			LOGGER.log(Level.INFO, "DataReceiverQueue - shutting down dataReceiver "
					+ drfq.getDataReceiver().getDelegate());
			drfq.shutdown();
			LOGGER.log(Level.INFO, "DataReceiverQueue - dataReceiver " + drfq.getDataReceiver().getDelegate()
					+ " is shut down!");
		}

		LOGGER.log(Level.INFO, "DataReceiverQueue - shut down completed!");
	}

	public boolean add(final DataReceiver dr, final IResource resource, final DataProducerState currentState)
			throws MaximumClientsReached, NotAuthorized {
		LOGGER.log(Level.INFO, "DataReceiverQueue - trying to register new dataReceiver!");

//		if (messageQueue.isStopdispatching()) {
//			LOGGER.log(Level.INFO, "DataReceiverQueue - The EventQueue is already stoped dispatching. "
//					+ "Can't register DataReceiver if ain't gonna be nothing more to dispatch!");
//			return false;
//		}

		boolean retVal = false;
		final DataReceiverForQueue drfq = new DataReceiverForQueue(dr, dataReceiverForQueueAdapter);

		synchronized (queueOrg) {
			if (queueOrg.size() >= getMaximumDataReceivers()) {
				LOGGER.log(
						Level.INFO,
						"DataReceiverQueue - Maximum capacity reached... Going to deny dataReceiver's request for registration! Maybe try later?");
				throw new MaximumClientsReached(
						MaximumClientsReachedConstants.MAXIMUM_RECEIVERS_REACHED_IN_DATA_PRODUCER,
						getMaximumDataReceivers());
			}

			LOGGER.log(Level.INFO, "DataReceiverQueue : checking to see if DataReceiver is allready registered!");

			if (contains(drfq)) {
				if (!drfq.isConnected()) {
					LOGGER.log(Level.INFO, "DataReceiverQueue : dataReceiver is not connected - shutting it down!");
					drfq.shutdown();
				} else {
					// datareceiver is allready registered... just ignore it...
					return true;
				}
			}

			LOGGER.log(Level.INFO, "DataReceiverQueue - Going to register dataReceiver!");
			retVal = queueOrg.add(drfq);
			LOGGER.log(Level.INFO, "DataReceiverQueue - registered dataReceiver "
					+ (retVal ? "successfully!" : "failed!"));
			LOGGER.log(Level.INFO,
					"DataReceiverQueue - Informing dataReceiver of current State "+currentState+" - just to get him goin'!");
			drfq.stateChanged(new DataProducerStateChangeEvent(currentState));

		}

		return retVal;
	}

	public Iterator<DataReceiverForQueue> iterator() {
		synchronized (queueOrg) {
			return Collections.unmodifiableList(new LinkedList<DataReceiverForQueue>(queueOrg)).iterator();
		}
	}

	public boolean remove(final DataReceiverForQueue drfq) {
		boolean returnVal = false;

		if (queueOrg == null || drfq == null) {
			return true;
		}

		synchronized (queueOrg) {
			try {
				if (queueOrg.contains(drfq)) {
					returnVal = queueOrg.remove(drfq);

					LOGGER.log(Level.INFO, "DataReceiverQueue - " + (returnVal ? "Removed" : "Failed to remove")
							+ " dataReceiver " + drfq.getDataReceiver().getDelegate() + "!");
					if (returnVal) {
						LOGGER.log(Level.INFO, "DataReceiverQueue - informing dataReceiver is gone "
								+ drfq.getDataReceiver().getDelegate() + "!");

						if (dataReceiverQueueListener != null) {
							dataReceiverQueueListener.oneDataReceiverForQueueIsGone();
						}
					}

				} else {
					LOGGER.log(Level.INFO, "DataReceiverQueue - dataReceiver " + drfq.getDataReceiver().getDelegate()
							+ " isn't registered here!");
				}

			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "DataReceiverQueue - Error removing dataReceiver "
						+ drfq.getDataReceiver().getDelegate() + "!", e);
			}
		}

		return returnVal;
	}

	public boolean contains(final DataReceiverForQueue drfq) {
		return queueOrg.contains(drfq);
	}

	public boolean isShutdown() {
		return messageQueue.isStopdispatching() && isDispatcherQueueStopdispatching();
	}

	private boolean isDispatcherQueueStopdispatching() {
		boolean ret = true;
		final Iterator<DataReceiverForQueue> queue = iterator();
		while (queue.hasNext()) {
			ret = ret && queue.next().isShutdown();
		}
		return ret;
	}

	public boolean isEmpty() {
		return !messageQueue.hasEvents() && isDispatcherQueueEmpty();
	}

	private boolean isDispatcherQueueEmpty() {
		boolean ret = true;
		final Iterator<DataReceiverForQueue> queue = iterator();
		while (queue.hasNext()) {
			ret = ret && queue.next().isEmpty();
		}
		return ret;
	}

	@Override
	public String toString() {
		final StringBuffer returnDataReceivers = new StringBuffer("");

		returnDataReceivers.append("******************************************" + "\r\n");
		returnDataReceivers.append("DataReceiver Queue Contents: \r\n");
		final Iterator<DataReceiverForQueue> iter = iterator();
		while (iter.hasNext()) {
			returnDataReceivers.append(iter.next() + "\r\n");
		}
		returnDataReceivers.append("******************************************" + "\r\n");
		return returnDataReceivers.toString();
	}

	/**
	 * Getter for property maximumDataReceivers.
	 * 
	 * @return Value of property maximumDataReceivers.
	 * 
	 */
	public int getMaximumDataReceivers() {
		return maximumDataReceivers;
	}

	/**
	 * Setter for property maximumDataReceivers.
	 * 
	 * @param maximumDataReceivers New value of property maximumDataReceivers.
	 * 
	 */
	public void setMaximumDataReceivers(final int maximumDataReceivers) {
		this.maximumDataReceivers = maximumDataReceivers;
	}

	/**
	 * Getter for property dataReceiverQueueListener.
	 * 
	 * @return Value of property dataReceiverQueueListener.
	 * 
	 */
	public com.linkare.rec.impl.multicast.IDataReceiverQueueListener getDataReceiverQueueListener() {
		return dataReceiverQueueListener;
	}

	/**
	 * Setter for property dataReceiverQueueListener.
	 * 
	 * @param dataReceiverQueueListener New value of property
	 *            dataReceiverQueueListener.
	 * 
	 */
	public void setDataReceiverQueueListener(
			final com.linkare.rec.impl.multicast.IDataReceiverQueueListener dataReceiverQueueListener) {
		this.dataReceiverQueueListener = dataReceiverQueueListener;
	}

	/* Inner Class - Queue Dispatcher */
	private class DataReceiverQueueDispatcher implements EventQueueDispatcher {
		private DataProducerState cachedDataProducerState = null;

		@Override
		public void dispatchEvent(final Object o) {
			if (o instanceof DataProducerStateChangeEvent) {
				final DataProducerStateChangeEvent evt = (DataProducerStateChangeEvent) o;
				if (cachedDataProducerState == null || !cachedDataProducerState.equals(evt.getDataProducerState())) {
					cachedDataProducerState = evt.getDataProducerState();

					LOGGER.log(
							Level.INFO,
							"DataReceiverQueue - dispatching DataProducer State change event. New State is: "
									+ evt.getDataProducerState());

					final Iterator<DataReceiverForQueue> clients = iterator();
					while (clients.hasNext()) {
						try {
							(clients.next()).stateChanged(evt);
						} catch (final Exception e) {
							LOGGER.log(
									Level.SEVERE,
									"DataReceiverQueue - Error dispatching DataProducer State change events to dataReceivers!",
									e);
						}
					}

					return;
				}
			} else if (o instanceof NewSamplesEvent) {
				final NewSamplesEvent evt = (NewSamplesEvent) o;
				LOGGER.log(Level.INFO, "DataReceiverQueue - dispatching new samples message event " + evt + " to "
						+ queueOrg.size() + " DataReceiverForQueue");

				final Iterator<DataReceiverForQueue> clients = iterator();
				while (clients.hasNext()) {
					try {
						(clients.next()).newSamples(evt);
					} catch (final Exception e) {
						LOGGER.log(Level.SEVERE, "DataReceiverQueue - Error dispatching new samples message event!", e);
					}
				}

				// verificar se e' um evento de paragem da thread
				if (evt.isPoisoned()) {
					LOGGER.log(Level.INFO, "DataReceiverQueue - shutting down message queue!");
					messageQueue.shutdown();
					LOGGER.log(Level.INFO, "DataReceiverQueue - shutting down dataReceivers connection checker!");
					dataReceiversConnectionChecker.shutdown();
				}
			}
		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}

	}

	/* End Inner Class - Queue Dispatcher */

	/* Inner Class - DataReceivers Connection Checker */
	private class DataReceiversConnectionCheck extends ScheduledWorkUnit {
		private final boolean shutdown = false;

		DataReceiversConnectionCheck() {
			ExecutorScheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
		}

		@Override
		public void run() {

			final Iterator<DataReceiverForQueue> iterDataReceivers = iterator();
			while (iterDataReceivers.hasNext() /* && !shutdown */) {
				try {
					final DataReceiverForQueue drfq = iterDataReceivers.next();
					if (!drfq.isConnected() && !shutdown) {
						drfq.shutdownAsSoonAsPossible();
					}
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, "DataReceiverQueue - Error cheking dataReceiver connection status!", e);
				}
			}

		}

	}

	/* End Inner Class - DataReceivers Connection Checker */

	/* Inner Class - DataReceivers callbacks */
	private class DataReceiverForQueueAdapter implements IDataReceiverForQueueListener {
		@Override
		public void dataReceiverForQueueIsGone(final DataReceiverForQueue drfq) {
			remove(drfq);
		}

	}
	/* End Inner Class - DataReceivers callbacks */
}
